import socket
from io import StringIO
import numpy as np
import _thread
from PretrainedModels import PretrainedModels
import os

pretrainedModel = PretrainedModels()


def get_video_feature(input=None):
    feature = pretrainedModel.featureExtractor(input)
    print(feature.shape)


def get_words_feature(input=None):
    feature = pretrainedModel.wordEncoder(input)
    print(feature.shape)


print("Hello from pretrained models")

# Receive data from edge device


# # data is received from sensor
# size = (2, 5)
# data = np.random.randint(0, 10, size=size)

# PORT = 10001
# with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
#     s.connect(('127.0.0.1', PORT))
#     # data = "Hello World"
#     # data = str(data.tolist())
#     data = str(size) + ";" + str(data.tolist())
#     s.sendall(bytes(data, encoding="utf-8"))

    # f = StringIO()
    # np.savez_compressed(f, frame=data)
    # f.seek(0)
    # out = f.read()
    # s.sendall(out)

frames = np.random.rand(1, 32, 1, 1).reshape(-1)
query = np.random.rand(1, 32).reshape(-1)
# frames = np.random.rand(12).reshape(-1)
# query = np.random.rand(1).reshape(-1)
# print(frames, np.array2string(frames, precision=2, separator=',', suppress_small=False).replace("[", "").replace("]", "").replace(" ", "").replace("\n", ""))
# print(query)
tmp = os.popen("java -cp /Users/jennyxu/Desktop/Delft/Q3/Distributed\ Systems/mmfilter/mmfilter.jar example.Main " +
               np.array2string(frames, precision=2, separator=',', suppress_small=False).replace("[", "").replace("]", "").replace(" ", "").replace("\n", "") + " " +
               np.array2string(query, precision=2, separator=',', suppress_small=False).replace("[", "").replace("]", "").replace(" ", "").replace("\n", "")).readlines()

print(tmp)

print("Bye from Python")
