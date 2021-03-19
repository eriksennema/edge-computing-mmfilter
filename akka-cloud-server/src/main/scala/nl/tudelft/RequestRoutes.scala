package nl.tudelft

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route

import scala.concurrent.Future
import UserRegistry._
import nl.tudelft.HandleRequest._
import akka.actor.typed.ActorRef
import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.AskPattern._
import akka.util.Timeout
import nl.tudelft.UserRegistry.{ActionPerformed, GetUserResponse, GetUsers}

//#import-json-formats
//#user-routes-class
class RequestRoutes(requestHander: ActorRef[HandleRequest.Command])(implicit val system: ActorSystem[_]) {

  //#user-routes-class
  import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
  import JsonFormats._
  //#import-json-formats

  // If ask takes more time than this to complete the request is failed
  private implicit val timeout = Timeout.create(system.settings.config.getDuration("my-app.routes.ask-timeout"))

  //#all-routes
  val requestRoutes: Route =
  pathPrefix("request") {
    post{
      complete("request received")
    }
  }
  //#all-routes
}
