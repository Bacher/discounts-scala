package me.supersale.collections

import scala.collection.mutable
import me.supersale.entries.Country

class Countries(list: Seq[Country]) {
  private val byId = mutable.Map[Long, Country]()

  for (country <- list) {
    byId(country.id) = country
  }

  def getById(id: Long) = byId(id)
}
