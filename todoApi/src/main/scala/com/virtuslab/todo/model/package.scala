package com.virtuslab.todo

import spray.json.DefaultJsonProtocol

package object model extends DefaultJsonProtocol {

  implicit val todoFormat = jsonFormat2(Todo.apply)

}
