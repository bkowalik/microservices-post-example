package com.virtuslab

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import com.google.inject.Guice
import com.virtuslab.notification.controller.NotificationController
import com.virtuslab.todo.controller.TodoController

import scala.concurrent.ExecutionContext
import scala.io.StdIn

object Main extends App {

  val injector = Guice.createInjector(new Module)
  implicit val ec = injector.getInstance(classOf[ExecutionContext])
  implicit val actorSystem = injector.getInstance(classOf[ActorSystem])
  implicit val actorMaterializer = ActorMaterializer()

  val todoController = injector.getInstance(classOf[TodoController])
  val notificationController = injector.getInstance(classOf[NotificationController])
  val routes = todoController.route ~ notificationController.route

  val bindingFuture = Http().bindAndHandle(routes, "localhost", 8080)
  println("Server online at http://localhost:8080/\nPress RETURN to stop...")
  StdIn.readChar()
  bindingFuture
    .flatMap(_.unbind())
    .onComplete(_ => actorSystem.terminate())

}
