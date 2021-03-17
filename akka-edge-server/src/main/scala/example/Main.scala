package example

import java.io.PrintStream
import java.net.{ServerSocket, Socket}

import scala.io.BufferedSource
import scala.util.control._
import net.{GatedEmbeddingUnit, TemporalAggregation}
import org.nd4j.linalg.factory.Nd4j

object Main extends App {
  println("Hello from main scala")
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


  val shape1 = Array(10, 1280, 8, 8)
  val frames = Nd4j.rand(shape1)  // data type: INDArray
  println("input: ", frames.shape())

  val shape2 = Array(1, 1280)
  val query_embedding = Nd4j.rand(shape2)
  println("input: ", query_embedding.shape())

  // test of pooling layer
  val averagePool = new TemporalAggregation
  val temporal_agg = averagePool.run(frames)

  // test of GEU
  val geu = new GatedEmbeddingUnit
  val embedding1 = geu.run(temporal_agg)
  val embedding2 = geu.run(query_embedding)

  val cos_dis = embedding1.mmul(embedding2.reshape(-1)).div(embedding1.norm2().mul(embedding2.norm2()))
  println("cosine dis: ", cos_dis)
}
