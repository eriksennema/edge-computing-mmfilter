package com.example

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpMethods, HttpRequest, HttpResponse}
import akka.stream.Materializer

import scala.concurrent.Future
import scala.concurrent.duration.DurationInt


object HttpDataSending {

  implicit val system = ActorSystem()
//  implicit val materializer = Materializer()
  import system.dispatcher

  val request = HttpRequest(
    method = HttpMethods.POST,
    uri = "127.0.0.1/request",
    entity = HttpEntity(
      ContentTypes.`application/json`,
      "{\"message\": \"Hello\"}"
    )
  )

  def sendRequest(): Future[String] = {
    val responseFuture: Future[HttpResponse] = Http().singleRequest(request)
    val entityFuture: Future[HttpEntity.Strict] = responseFuture.flatMap(respone => respone.entity.toStrict(2.seconds))
    entityFuture.map(entity => entity.data.utf8String)
  }

  def main(args: Array[String]): Unit = {
    sendRequest().foreach(println)
  }

}
