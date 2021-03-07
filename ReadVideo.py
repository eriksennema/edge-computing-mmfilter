'''Read a video and store it as a numpy array'''
import cv2
import numpy as np

def read_frame(video_dir):
    cap = cv2.VideoCapture(video_dir)
    if cap.isOpened():
        frameCount = int(cap.get(cv2.CAP_PROP_FRAME_COUNT))
        frameWidth = int(cap.get(cv2.CAP_PROP_FRAME_WIDTH))
        frameHeight = int(cap.get(cv2.CAP_PROP_FRAME_HEIGHT))
        # print(f"framecount: {frameCount}\nframeWidth: {frameWidth}\nframeHeight:{frameHeight}\n")

        buf = np.empty((frameCount, frameHeight, frameWidth, 3), np.dtype('uint8'))

        fc = 0
        ret = True

        while (fc < frameCount and ret):
            ret, buf[fc] = cap.read()
            fc += 1 
        cap.release()
        return buf
    else:
        print("Failed to open video!")


if __name__ == "__main__":
    # Test if read correctly
    video_dir = './test_video.mp4'
    buf = read_frame(video_dir)
    cv2.namedWindow('frame 10')
    cv2.imshow('frame 10', buf[9])
