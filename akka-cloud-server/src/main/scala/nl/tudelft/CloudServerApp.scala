package nl.tudelft

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._

import scala.util.Failure
import scala.util.Success

object CloudServerApp {

  private def startHttpServer(routes: Route)(implicit system: ActorSystem[_]): Unit = {
    // ActorSystem to start
    import system.executionContext

    val futureBinding = Http().newServerAt("localhost", 8080).bind(routes)
    futureBinding.onComplete {
          //Succesfull startup
      case Success(binding) =>
        val address = binding.localAddress
        system.log.info("Server online at http://{}:{}/", address.getHostString, address.getPort)
          //Failed to startup
      case Failure(ex) =>
        system.log.error("Failed to bind HTTP endpoint, terminating system", ex)
        system.terminate()
    }
  }
  def main(args: Array[String]): Unit = {


    val rootBehavior = Behaviors.setup[Nothing] { context =>

//      val handleRequestActor = context.spawn(HandleRequest(), "UserRegistryActor")
//      context.watch(handleRequestActor)

      import java.io.PrintStream
      import java.io.File
      val o = new PrintStream(new File("SavedData.txt"))

//      val console = Symbol.out

      System.setOut(o)
//      val routes = new RequestRoutes(handleRequestActor)(context.system)
      val routes = pathPrefix("request") {
        post{
            entity(as[String]) {
              json => System.out.println(json)
                complete("{\"status\": 200, \"message\": \"You did a POST request and your data is stored\"}")
            }
        }
      }


      //(path("request") & GET) {complete("{\"status\": 200, \"message\": \"You did a GET request\"}")}
      startHttpServer(routes)(context.system)

      Behaviors.empty
    }

    val system = ActorSystem[Nothing](rootBehavior, "AkkaHttpCloudServer") // ActorSystem[Nothing](rootBehavior, "AkkaHttpCloudServer")
  }
}
