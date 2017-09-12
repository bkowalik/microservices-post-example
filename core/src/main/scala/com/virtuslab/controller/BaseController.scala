package com.virtuslab.controller

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.server.{Directives, Route}

abstract class BaseController extends Directives with SprayJsonSupport {

  def route: Route

}
