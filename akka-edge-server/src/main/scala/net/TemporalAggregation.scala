package net

import scorch.autograd.Variable
import org.nd4j.linalg.api.ndarray.INDArray
import org.deeplearning4j.nn.conf.{BackpropType, MultiLayerConfiguration, NeuralNetConfiguration}
import org.deeplearning4j.nn.conf.layers.{GlobalPoolingLayer, OutputLayer, PoolingType}
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork
import org.nd4j.linalg.activations.Activation
import org.nd4j.linalg.lossfunctions.LossFunctions.LossFunction
import org.nd4j.linalg.factory.Nd4j
import org.nd4j.linalg.api.ndarray.INDArray
import java.util

class TemporalAggregation {

  val seed = 6

  var averagePoolConf = new GlobalPoolingLayer()
  averagePoolConf.setCollapseDimensions(true)
  averagePoolConf.setPoolingType(PoolingType.AVG)
  averagePoolConf.setPoolingDimensions(Array(0, 2, 3))

  var conf: NeuralNetConfiguration = new NeuralNetConfiguration.Builder()
    .seed(seed)
    .layer(averagePoolConf)
    .build()

  val confs: util.List[NeuralNetConfiguration] = new util.LinkedList()
  confs.add(conf)
  val builder = new MultiLayerConfiguration.Builder()
  builder.setConfs(confs)

  val model: MultiLayerNetwork = new MultiLayerNetwork(builder.build())

  def run(input: INDArray): INDArray = {
    model.init()
    val output = model.output(input)
    println(output.shapeInfoToString())

    output
  }
}
