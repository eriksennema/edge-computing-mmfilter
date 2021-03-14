package net

import java.util

import org.deeplearning4j.nn.conf.layers.DenseLayer
import org.deeplearning4j.nn.conf.{MultiLayerConfiguration, NeuralNetConfiguration}
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork
import org.nd4j.linalg.activations.Activation
import org.nd4j.linalg.factory.Nd4j
import org.nd4j.linalg.api.ndarray.INDArray

class GatedEmbeddingUnit {

  val seed = 6
  val dim1 = 1280
  val dim2 = 256

  var conf_layer1: NeuralNetConfiguration = new NeuralNetConfiguration.Builder()
    .seed(seed)
    .layer(new DenseLayer.Builder().nIn(dim1).nOut(dim2)
      .build())
    .build()

  var conf_layer2: NeuralNetConfiguration = new NeuralNetConfiguration.Builder()
    .seed(seed)
    .layer(new DenseLayer.Builder().nIn(dim2).nOut(dim2)
      .build())
    .activation(Activation.SIGMOID)
    .build()

  val confs1: util.List[NeuralNetConfiguration] = new util.LinkedList()
  confs1.add(conf_layer1)
  val builder1 = new MultiLayerConfiguration.Builder()
  builder1.setConfs(confs1)
  val model1: MultiLayerNetwork = new MultiLayerNetwork(builder1.build())

  val confs2: util.List[NeuralNetConfiguration] = new util.LinkedList()
  confs2.add(conf_layer2)
  val builder2 = new MultiLayerConfiguration.Builder()
  builder2.setConfs(confs2)
  val model2: MultiLayerNetwork = new MultiLayerNetwork(builder2.build())

  def run(input: INDArray): INDArray = {
    println(model1, model2)
    model1.init()
    model2.init()

    val embedding1 = model1.output(input)
    println(embedding1.shapeInfoToString())

    val output = model2.output(embedding1)
    val embedding2 = output.mul(embedding1)
    println(embedding2.shapeInfoToString())

    val embedding = embedding2.div(embedding2.norm2())
    println(embedding.shapeInfoToString())

    embedding
  }
}
