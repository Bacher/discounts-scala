package me.supersale

import scalikejdbc._

object User {
  def loadById(id: Long) = {
    implicit val session = Discounts.getDBSession

    val users = sql"""
          SELECT `name`
          FROM `user`
          WHERE `id` = $id
          LIMIT 1"""
      .map(_.toMap).list.apply()

    if (users.isEmpty) {
      throw new RequestError
    } else {
      val fields = users.head

      new User(id, fields("name").asInstanceOf[String])
    }
  }
}

class User(val id: Long, val name: String) {

}
