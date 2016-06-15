package me.supersale.loader

import me.supersale.{Discounts, entries}
import scalikejdbc._

class CityLoader {

  def load(loader: Loader) = {
    implicit val session = Discounts.getDBSession

    val data =
      sql"""
        SELECT `id`, `title`, `country_id`
        FROM `city`
        WHERE `active` = "yes" """
        .map(_.toMap).list.apply()

    data.map(fields => {
      new entries.City(
        fields("id").asInstanceOf[Long],
        loader.countries.getById(fields("country_id").asInstanceOf[Long]),
        fields("title").asInstanceOf[String]
      )
    })
  }
}
