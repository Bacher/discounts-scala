package me.supersale.api

import me.supersale._
import spray.json._

object getUserInfo extends apiMethod {
  def exec(params: JsObject, user: User, client: Client): JsObject = {
    JsObject("status" -> JsString("OK"))
  }
}
