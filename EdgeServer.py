import socket
import numpy
import cv2
import time

HOST = ''
PORT = 50007
    
with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as server:
     print('Edge server started, waiting for requests')
     server.bind((HOST, PORT))
     server.listen(1)
     client, address = server.accept()
     with client:
         print(f'Connected by {address}')
         count = 0
         total_frame = int.from_bytes(client.recv(2), 'big')
         time.sleep(1)
         buffer = numpy.empty((total_frame, 240, 320, 3), numpy.dtype('uint8')) 
         while count < total_frame:
             length = int.from_bytes(client.recv(2), 'big')
             time.sleep(1)
             bytes_data = client.recv(length)
             time.sleep(1)
             if bytes_data:
                 data = numpy.frombuffer(bytes_data, dtype=numpy.uint8)
                 frame = cv2.imdecode(data, cv2.IMREAD_COLOR)
                 buffer[count] = frame
                 #cv2.imshow(f'frame {count}', buffer[count])
                 count += 1
                 print(f'Received frame {count}.')
             else:
                 break
         print(f'Server received {count} frames.')
