package com.virtuslab.notification.actor

import akka.actor.{Actor, ActorRef, Props, Terminated}
import com.virtuslab.notification.actor.WsSupervisor.{Dispatch, Register}

class WsSupervisor extends Actor {

  def receiveWithListeners(listeners: Map[String, ActorRef] = Map.empty): Receive = {
    case Register(ws, name) =>
      val newListeners = listeners.get(name).map(_ => listeners).getOrElse {
        context.watch(ws)

        listeners + (name -> ws)
      }
      context.become(receiveWithListeners(newListeners))

    case Terminated(listener) =>
      val newListeners = listeners.filterNot {
        case (_, ref) => ref == listener
      }
      context.become(receiveWithListeners(newListeners))
    case Dispatch(name, msg) =>
      listeners.find(_._1 == name).foreach { case (_, ref) =>
        ref ! msg
      }
  }

  def receive: Receive = receiveWithListeners()

}

object WsSupervisor {
  sealed trait WsSupervisorProtocol
  final case class Register(ref: ActorRef, name: String) extends WsSupervisorProtocol
  final case class Dispatch(to: String, msg: AnyRef) extends WsSupervisorProtocol

  def props = Props(new WsSupervisor)

  final val Name = "WsSupervisor"
}
