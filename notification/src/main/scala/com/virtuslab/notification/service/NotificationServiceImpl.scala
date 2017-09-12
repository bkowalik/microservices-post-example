package com.virtuslab.notification.service

import javax.inject.{Inject, Named, Singleton}

import spray.json._
import akka.actor.{ActorRef, ActorSystem}
import akka.http.scaladsl.model.ws.{Message, TextMessage}
import akka.stream.OverflowStrategy
import akka.stream.scaladsl.{Flow, Sink, Source}
import com.virtuslab.notification.actor.WsSupervisor.{Dispatch, Register}
import com.virtuslab.notification.actor.WsSupervisor
import com.virtuslab.notification.model._

import scala.concurrent.Future

@Singleton
class NotificationServiceImpl @Inject()(
 actorSystem: ActorSystem,
 @Named(WsSupervisor.Name) wsSupervisor: ActorRef
) extends NotificationService {

  override def send(notification: Notification): Future[Unit] = Future.successful {
    wsSupervisor ! Dispatch(notification.who, notification)
  }

  override def subscribe(name: String): Flow[Message, Message, Any] = {
    val client = Source.actorRef[Notification](10, OverflowStrategy.dropTail)
      .mapMaterializedValue{ ref =>
        println(ref)
        wsSupervisor ! Register(ref, name)
      }.map { notification =>
        TextMessage(notification.toJson.compactPrint)
      }
    Flow.fromSinkAndSource(Sink.ignore, client)
  }
}
