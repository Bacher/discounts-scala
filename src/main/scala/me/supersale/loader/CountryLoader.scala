package me.supersale.loader

import me.supersale.Discounts
import me.supersale.entries
import scalikejdbc._

class CountryLoader {

  def load(loader: Loader) = {
    implicit val session = Discounts.getDBSession

    val countriesData =
      sql"""
        SELECT `id`, `title`
        FROM `country`
        WHERE `active` = "yes" """
        .map(_.toMap).list.apply()

    val countries = countriesData.map(fields => {
      new entries.Country(fields("id").asInstanceOf[Long], fields("title").asInstanceOf[String])
    })

    countries
  }
}
