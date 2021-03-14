'''Read video feeds, send data to the edge server by frames'''
import cv2
import socket
import numpy
import time

HOST = 'localhost'
PORT = 50007
server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
print('Edge device sends a connection request to the edge server.')
server.connect((HOST, PORT))
print('Connection established.')
    
def frame_to_bytes(frame):
    encode_param = [int(cv2.IMWRITE_JPEG_QUALITY), 50] # Image quality ranges from 0 to 100
    frame_encode = cv2.imencode('.jpg', frame, encode_param)[1]
    bytes_data = frame_encode.tobytes()
    return bytes_data
                    
def process_video(video_dir):
    capture = cv2.VideoCapture(video_dir)
    if capture.isOpened():
        total_frame = int(capture.get(cv2.CAP_PROP_FRAME_COUNT))
        server.send(total_frame.to_bytes(2, 'big')) # Send total number of frames
        time.sleep(1)
        count = 0
        while True:
            success, frame = capture.read() # Read one frame
            if success:
                frame = cv2.resize(frame, (320, 240))
                bytes_data = frame_to_bytes(frame)
                length = len(bytes_data).to_bytes(2, 'big')
                server.send(length)
                time.sleep(1)
                server.send(bytes_data)
                time.sleep(1)
                count += 1
                print(f'Frame {count} has been sent.')
                #cv2.imshow(f'frame {count}', frame)
            else:
                if count == total_frame:
                    print('All frames have been sent.')
                else:
                    print('Failed to read frames.')
                break
    else:
        print("Failed to capture video!")
    cv2.destroyAllWindows()
    server.close()

if __name__ == "__main__":
    video_dir = './test_video_Trim.mp4'
    process_video(video_dir)
