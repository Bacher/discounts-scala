package me.supersale.collections

import me.supersale.entries.City

import scala.collection.mutable

class Cities(list: Seq[City]) {
  private val byId = mutable.Map[Long, City]()

  for (city <- list) {
    byId(city.id) = city
  }

  def getById(id: Long) = byId(id)
}
