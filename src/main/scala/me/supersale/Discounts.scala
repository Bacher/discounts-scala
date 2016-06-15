package me.supersale

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scalikejdbc._
import me.supersale.loader.Loader
import spray.json.{JsObject, _}

object Discounts {
  ConnectionPool.singleton("jdbc:mysql://localhost:3306/discounts", "root", "")

  private implicit val session = AutoSession

  var inst: Discounts = null

  def getDBSession = session
}

class Discounts {
  val clients = new Clients
  val users = new Users

  Discounts.inst = this

  val loader = new Loader()
  loader.loadAll()

//  try {
//    println(clients.getByToken("123"))
//  } catch {
//    case err: Throwable => err.printStackTrace()
//  }

  def handleApiRequest(apiName: String, params: JsObject): JsObject = {
    try {
      val clientInfo = if (params.fields.contains("token")) {
        val token = params.fields("token") match {
          case JsString(value) => value
          case _ => throw new RequestError
        }

        val client = clients.getByToken(token)
        (client.user, client)
      } else {
        (null, null)
      }

      JsObject("r" -> Apis.callApi(apiName, params, clientInfo._1, clientInfo._2))
    } catch {
      case err: RequestError =>
        err.printStackTrace()
        JsObject("e" -> err.toJson)
      case err: Throwable =>
        err.printStackTrace()
        JsObject("e" -> new RequestError().toJson)
    }
  }
}
