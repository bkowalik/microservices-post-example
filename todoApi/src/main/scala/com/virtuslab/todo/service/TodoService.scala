package com.virtuslab.todo.service

import com.virtuslab.todo.model.Todo

import scala.concurrent.Future

trait TodoService {

  def add(todo: Todo): Future[Unit]
}
