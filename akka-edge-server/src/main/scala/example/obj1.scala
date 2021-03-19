package example

import jdk.nashorn.internal.ir.IndexNode
import botkop.{numsca => ns}
import scorch._
import scorch.autograd.Variable
import scorch.nn.cnn._
import scorch.nn._
import scorch.optim.SGD

class obj1 {

  def receive(list: String): Unit = {
    //    println(list.split(";")(0).toString())
    val unsolved_dim = list.split(";")(0).toString()
    val dim = unsolved_dim.substring(unsolved_dim.indexOf("(")+1, unsolved_dim.indexOf(")"))
    val dims = dim.split(",")
    val dim_length = dims.length
    println(Integer.parseInt(dims(0).toString.trim))
    println(Integer.parseInt(dims(1).toString.trim))
    val array = list.split(";")(1).toString()
//    dim_length match {
//      case 2 =>
//        getList(, , array)
//      case 3 =>
//        getList(, , array)
//    }



  }

  def getList(first_dim: Int, second_dim:Int, array: String): Unit = {
    var first_index = 0
    for(i <- 1 to first_dim){
      first_index = array.indexOf("[")
    }

  }

  /*
  / frames: N consecutive frames
   */
  def temporalAggregation(frames: Variable): Unit = {

  }

  def run(): Unit = {
    val x = Variable(ns.ones(2,2))
    val y = x + 2
    val z = y * y * 3
    val out = z.mean()
    out.backward()
    println(x.grad)
    println("Hello from obj1")
    receive("[(2, 5); [[1, 2, 3, 4, 5], [3, 4, 5, 6, 7]]")
  }
}

