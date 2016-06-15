package me.supersale

import scala.collection.mutable

class Users {
  private val users = mutable.Map[Long, User]()

  def getById(id: Long): User = {
    if (users.contains(id))
      users(id)
    else {
      val user = User.loadById(id)
      users(id) = user
      user
    }
  }
}
