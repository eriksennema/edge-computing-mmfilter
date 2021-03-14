package nl.tudelft

import akka.actor.ActorRef
import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors

object HandleRequest {

  sealed trait Command
  final case class executeFunction(replyTo: ActorRef) extends Command

  def apply(): Behavior[Command] = registry(Set.empty)

  private def registry(value: Set[Nothing]) : Behavior[Command] =
    Behaviors.receiveMessage {
      case executeFunction(replyTo) =>
        Behaviors.same
      case _ =>
        Behaviors.same
    }
}
