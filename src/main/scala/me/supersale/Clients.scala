package me.supersale

import scala.concurrent.ExecutionContext.Implicits.global
import scala.collection.mutable
import scala.concurrent.Future

class Clients {
  private val clientsById = mutable.Map[Long, Client]()
  private val clientsByToken = mutable.Map[String, Client]()

  def getByToken(token: String): Client = {
    if (clientsByToken.contains(token))
      clientsByToken(token)
    else {
      val client = Client.loadByToken(token)
      clientsByToken(token) = client
      clientsById(client.id) = client
      client
    }
  }
}
