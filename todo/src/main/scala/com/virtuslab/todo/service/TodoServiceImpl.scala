package com.virtuslab.todo.service

import javax.inject.{Inject, Singleton}

import com.virtuslab.notification.model.Notification
import com.virtuslab.notification.service.NotificationService
import com.virtuslab.todo.model.Todo
import com.virtuslab.todo.repository.TodoRepository

import scala.concurrent.Future

@Singleton
class TodoServiceImpl @Inject() (
  notificationService: NotificationService,
  todoRepository: TodoRepository
) extends TodoService {

  override def add(todo: Todo): Future[Unit] = {
    val notification = Notification(todo.who, todo.content)
    notificationService.send(notification)
  }

}
