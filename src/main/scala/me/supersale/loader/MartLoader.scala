package me.supersale.loader

import me.supersale.{Discounts, Position, entries}
import scalikejdbc._

class MartLoader {

  def load(loader: Loader) = {
    implicit val session = Discounts.getDBSession

    val data =
      sql"""
        SELECT `id`, `title`, `city_id`, `address_show`, IF(`photo`,`photo`,"") AS photo, X(`address_geo`) AS x, Y(`address_geo`) AS y
        FROM `shop_center`
        WHERE `active` = "yes" """
        .map(_.toMap).list.apply()

    data.flatMap(fields => {
      if (!fields.contains("x")) {
        println(s"Mart ${fields("id")} has no address_geo. SKIP")
        Seq()
      } else {
        Seq(new entries.Mart(
          id = fields("id").asInstanceOf[Long],
          country = loader.countries.getById(1), //fields("country_id").asInstanceOf[Long]),
          city = loader.cities.getById(fields("city_id").asInstanceOf[Long]),
          title = fields("title").asInstanceOf[String],
          position = new Position(fields("x").asInstanceOf[Double], fields("y").asInstanceOf[Double]),
          address = fields("address_show").asInstanceOf[String],
          picture = fields("photo").asInstanceOf[String]
        ))
      }
    })
  }
}
