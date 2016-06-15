package me.supersale.collections

import me.supersale.entries.Mart

import scala.collection.mutable

class Marts(list: Seq[Mart]) {
  private val byId = mutable.Map[Long, Mart]()

  for (mart <- list) {
    byId(mart.id) = mart
  }

  def getById(id: Long) = byId(id)
}
