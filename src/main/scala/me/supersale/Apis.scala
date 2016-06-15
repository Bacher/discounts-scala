package me.supersale
import spray.json._

object Apis {
  def callApi(apiName: String, apiParams: JsObject, user: User, client: Client): JsObject = {
    val method = apiName match {
      case "getUserInfo.json" => api.getUserInfo
      case _ => throw new InvalidApi
    }

    if (method.onlyAuthorized && client == null)
      throw new UnauthorizedAccess

    method.exec(apiParams, user, client)
  }
}
