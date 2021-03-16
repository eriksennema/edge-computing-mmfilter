import socket
import numpy
import cv2
import time
import threading

HOST = ''
PORT = 50007

def tcplink(client, address):
    print(f'Connected by {address}')
    client.send(b'Welcome to the edge server!')
    count = 0
    # total_frame = int.from_bytes(client.recv(2), 'big')
    # time.sleep(1)
    # buffer = numpy.empty((total_frame, 240, 320, 3), numpy.dtype('uint8')) 
    # while count < total_frame:
    while True:
        length = int.from_bytes(client.recv(2), 'big')
        time.sleep(1)
        bytes_data = client.recv(length)
        time.sleep(1)
        if bytes_data:
            data = numpy.frombuffer(bytes_data, dtype=numpy.uint8)
            frame = cv2.imdecode(data, cv2.IMREAD_COLOR)
            # buffer[count] = frame
            count += 1
            print(f'Received frame {count} from {address}.')
        else:
            break
    client.close()
    print(f'Connection from {address} is closed.')
    print(f'Server received {count} frames from {address}.')

server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
server.bind((HOST, PORT))
server.listen(5)
print('Edge server started, waiting for requests')
while True:
    client, address = server.accept()
    t = threading.Thread(target=tcplink, args=(client, address))
    t.start()
