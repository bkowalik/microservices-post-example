package com.virtuslab

import javax.inject.{Inject, Provider}

import akka.actor.{ActorRef, ActorSystem}
import com.google.inject.AbstractModule
import com.google.inject.name.Names
import com.virtuslab.notification.actor.WsSupervisor
import com.virtuslab.notification.service.{NotificationService, NotificationServiceImpl}
import com.virtuslab.todo.service.{TodoService, TodoServiceImpl}

import scala.concurrent.ExecutionContext

class Module extends AbstractModule {
  override def configure(): Unit =  {
    bind(classOf[ActorSystem]).toProvider(classOf[ActorSystemProvider])
    bind(classOf[ExecutionContext]).toProvider(classOf[ExecutionContextProvider])

    bind(classOf[ActorRef])
      .annotatedWith(Names.named(WsSupervisor.Name))
      .toProvider(classOf[WsSupervisorProvider])

    bind(classOf[NotificationService]).to(classOf[NotificationServiceImpl])
    bind(classOf[TodoService]).to(classOf[TodoServiceImpl])
  }
}

class ActorSystemProvider extends Provider[ActorSystem] {

  private lazy val instance = ActorSystem("example-system")

  override def get(): ActorSystem = instance
}

class ExecutionContextProvider extends Provider[ExecutionContext] {

  private lazy val instance = ExecutionContext.global

  override def get(): ExecutionContext = instance
}

class WsSupervisorProvider @Inject() (actorSystem: ActorSystem) extends Provider[ActorRef] {

  private lazy val instance = actorSystem.actorOf(WsSupervisor.props)

  override def get(): ActorRef = instance
}
