package com.virtuslab.notification.controller

import javax.inject.{Inject, Singleton}

import com.virtuslab.controller.BaseController
import com.virtuslab.notification.service.NotificationService

@Singleton
class NotificationController @Inject() (notificationService: NotificationService) extends BaseController {

  val route = path("notification" / Remaining) { name =>
    handleWebSocketMessages(notificationService.subscribe(name))
  }

}
