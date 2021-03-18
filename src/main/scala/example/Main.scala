package example

import java.io.PrintStream
import java.net.{ServerSocket, Socket}

import scala.io.BufferedSource
import scala.util.control._
import scala.io._
import scala.util._
import net.{GatedEmbeddingUnit, TemporalAggregation}
import org.nd4j.linalg.api.ndarray.INDArray
import org.nd4j.linalg.factory.Nd4j

object Main extends App {
  println("Hello from main scala")

//  while (true) {
//    println("Hello.")
//  }

//  val nums = Source.stdin.getLines.flatMap(v => Try(v.toString).toOption)
//  println(s"cmd input: ", nums)
//  println(s"cmd input: ", nums.toList)

//  for (ln <- io.Source.stdin.getLines) {
//    println("cmd input: ", ln.toList)
//  }

//  val iter = Iterator("123", "345")
//
//  // Applying toList method
//  val result = iter.toList
//
//  // Displays output
//  println(result)

//  val loop = new Breaks
//
//  var obj = new obj1
//  obj.run()

//  var msg = new String
//  // socket server part
//  val listener: ServerSocket = new ServerSocket(10001)
//  println("Socket server is working...")
//  loop.breakable{
//    while (true) {
//      // create socket
//      val socket: Socket = listener.accept()
//
//      val in = new BufferedSource(socket.getInputStream()).getLines()
//      val out = new PrintStream(socket.getOutputStream())
//
//      msg = in.next()
//      println(msg)
//      socket.close()
//      loop.break()
//    }
//  }
//  println("Server closed...")
//  obj.receive(msg)

//  def generate_random():(INDArray, INDArray) = {
//    val shape1 = Array(12, 1280, 8, 8)
//    val frames = Nd4j.rand(shape1)  // data type: INDArray
//    println("input: ", frames.shapeInfoToString())
//    val shape2 = Array(1, 1280)
//    val query_embedding = Nd4j.rand(shape2)
//    println("input: ", query_embedding.shapeInfoToString())
//    (frames, query_embedding)
//  }

//  var frames_str: String = _
//  var query_str: String = _
//  if (args.length < 2){
//    println("ERROR! NEED INPUT!")
//    System.exit(0)
//  } else {
//    frames_str = args(0)  // expect format: 1*12*1280*8*8
////    println("frames: ", frames_str)
//    query_str = args(1)  // expect format: 1280
////    println("query: ", query_str)
//  }

  var frames_str: String = "1,2,3,4,5"
  var query_str: String = "1,2,3,4,5,6,7"

  val frames_array: Array[Double] = frames_str.split(",").map(_.toDouble)
  val frames = Nd4j.create(frames_array)
  println("input: ", frames.shapeInfoToString())

  val query_array: Array[Double] = query_str.split(",").map(_.toDouble)
  val query_embedding = Nd4j.create(query_array)
  println("input: ", query_embedding.shapeInfoToString())

//  // test of pooling layer
//  val averagePool = new TemporalAggregation
//  val temporal_agg = averagePool.run(frames)
//
//  // test of GEU
//  val geu = new GatedEmbeddingUnit
//  val embedding1 = geu.run(temporal_agg)
//  val embedding2 = geu.run(query_embedding)
//
//  val cos_dis = embedding1.mmul(embedding2.reshape(-1)).div(embedding1.norm2().mul(embedding2.norm2()))
//  println("cosine dis: ", cos_dis)
}
