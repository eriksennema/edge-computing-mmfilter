from transformers import BertTokenizer, BertModel, BertConfig
import torch.nn as nn


class WordEncoder():
    def __init__(self):
        self.tokenizer = BertTokenizer.from_pretrained('bert-base-uncased')
        self.configuration = BertConfig()
        self.model = BertModel(self.configuration)
        self.featureExtractor = nn.Linear(768, 1280)

    def run(self, sentence=None):
        if sentence is None:
            sentence = "Hello, my dog is cute"
        inputs = self.tokenizer(sentence, return_tensors="pt")
        outputs = self.model(**inputs)
        last_hidden_states = outputs.last_hidden_state
        query_embedding = self.featureExtractor(last_hidden_states[0, -1, :])
        # print(query_embedding.shape)
        return query_embedding
