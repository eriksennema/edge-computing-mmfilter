import torch
import torch.nn as nn


class FeatureExtractor():
    def __init__(self):
        mobilenet_v2 = torch.hub.load('pytorch/vision:v0.5.0', 'mobilenet_v2', pretrained=False)
        mobilenet_v2 = list(mobilenet_v2.children())[:-1]  # Remove the last FC(2048, 1000)
        self.model = nn.Sequential(*mobilenet_v2)
        self.model.eval()

    def run(self, input_data=None):
        if input_data is None:
            input_data = torch.rand(10, 3, 256, 256, dtype=torch.float32)

        output_data = self.model(input_data)
        # print(output_data.shape)
        return output_data
