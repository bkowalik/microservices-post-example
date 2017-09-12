package com.virtuslab.notification

import spray.json._

package object model extends DefaultJsonProtocol {

  implicit val notificationFormat = jsonFormat2(Notification.apply)

}
