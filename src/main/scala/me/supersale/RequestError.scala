package me.supersale

import spray.json._

class RequestError extends Exception {
  val code = "UNKNOWN_ERROR"
  val message = "Fuck me up!"

  def toJson = {
    JsObject(
      "code" -> JsString("E_" + this.code),
      "message" -> JsString(this.message)
    )
  }
}

class InvalidApi extends RequestError {
  override val code = "INVALID_API"
  override val message = "Invalid api call"
}

class UnauthorizedAccess extends RequestError {
  override val code = "UNAUTHORIZED_ACCESS"
  override val message = "Need autorization"
}