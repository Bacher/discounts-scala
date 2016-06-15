package me.supersale

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scalikejdbc._
import spray.json._

object Client {
  def loadByToken(token: String): Client = {
    implicit val session = Discounts.getDBSession

    val clients = sql"""
          SELECT `id`, `user_id`
          FROM `client`
          WHERE `token` = $token
          LIMIT 1"""
      .map(_.toMap).list.apply()

    if (clients.isEmpty) {
      throw new RequestError
    } else {
      val fields = clients.head

      val userId = fields("user_id").asInstanceOf[Long]

      var user = Discounts.inst.users.getById(userId)

      new Client(fields("id").asInstanceOf[Long], userId, token, user)
    }
  }
}

class Client(val id: Long, val userId: Long, val token: String, val user: User) {
  def toJson = {
    JsObject (
      "id" -> JsNumber(this.id),
      "userId" -> JsNumber(this.userId),
      "token" -> JsString(this.token)
    )
  }
}
