import socket
import numpy
import cv2
import time
import torch
from PIL import Image
from torchvision import transforms

HOST = ''
PORT = 50007


def classifier(frame):
    # input_image = Image.fromarray(numpy.uint8(frame)).convert('RGB')
    input_image = Image.fromarray(frame.astype('uint8'), 'RGB')
    preprocess = transforms.Compose([
        transforms.Resize(256),
        transforms.CenterCrop(224),
        transforms.ToTensor(),
        transforms.Normalize(mean=[0.485, 0.456, 0.406], std=[0.229, 0.224, 0.225]),
    ])
    input_tensor = preprocess(input_image)
    input_batch = input_tensor.unsqueeze(0)  # create a mini-batch as expected by the model

    # move the input and model to GPU for speed if available
    if torch.cuda.is_available():
        input_batch = input_batch.to('cuda')
        model.to('cuda')

    with torch.no_grad():
        output = model(input_batch)
    # Tensor of shape 1000, with confidence scores over Imagenet's 1000 classes

    # The output has unnormalized scores. To get probabilities, you can run a softmax on it.
    probabilities = torch.nn.functional.softmax(output[0], dim=0)

    # Read the categories
    with open("labels.txt", "r") as f:
        categories = [s.strip() for s in f.readlines()]
    # Show top categories per image
    top_prob, top_catid = torch.topk(probabilities, 1)
    # print(categories[top_catid])

    return categories[top_catid]

with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as server:
    print('Edge server started, waiting for requests')
    server.bind((HOST, PORT))
    server.listen(1)
    client, address = server.accept()
    model = torch.hub.load('pytorch/vision:v0.6.0', 'mobilenet_v2', pretrained=True)
    model.eval()
    prev_label = ""
    with client:
        print(f'Connected by {address}')
        count = 0
        total_frame = int.from_bytes(client.recv(2), 'big')
        # time.sleep(1)
        buffer = numpy.empty((total_frame, 540, 960, 3), numpy.dtype('uint8'))
        label_buf = []
        while count < total_frame:
            length = int.from_bytes(client.recv(2), 'big')
            # time.sleep(1)
            bytes_data = client.recv(length)
            # time.sleep(1)
            if bytes_data:
                data = numpy.frombuffer(bytes_data, dtype=numpy.uint8)
                frame = cv2.imdecode(data, cv2.IMREAD_COLOR)
                curr_label = classifier(frame)
                if curr_label != prev_label:
                    print("Label: {0:20} Frame: {1}".format(curr_label, count))
                prev_label = curr_label
                buffer[count] = frame
                # cv2.imshow(f'frame {count}', buffer[count])
                count += 1
                # print(f'Received frame {count}.')
            else:
                break
        print(f'Server received {count} frames.')
