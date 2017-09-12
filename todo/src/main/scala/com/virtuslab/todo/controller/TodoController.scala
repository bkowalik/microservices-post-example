package com.virtuslab.todo.controller

import javax.inject.{Inject, Singleton}

import akka.http.scaladsl.model.StatusCodes
import com.virtuslab.controller.BaseController
import com.virtuslab.todo.model._
import com.virtuslab.todo.service.TodoService

@Singleton
class TodoController @Inject() (todoService: TodoService) extends BaseController {

  val route = path("todo") {
    post {
      entity(as[Todo]) { todo =>
        onSuccess(todoService.add(todo)) {
          complete(StatusCodes.Created)
        }
      }
    }
  }

}
