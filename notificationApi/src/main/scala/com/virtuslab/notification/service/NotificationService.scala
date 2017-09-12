package com.virtuslab.notification.service

import akka.http.scaladsl.model.ws.Message
import akka.stream.scaladsl.Flow
import com.virtuslab.notification.model.Notification

import scala.concurrent.Future

trait NotificationService {

  def send(notification: Notification): Future[Unit]

  def subscribe(name: String): Flow[Message, Message, Any]

}
