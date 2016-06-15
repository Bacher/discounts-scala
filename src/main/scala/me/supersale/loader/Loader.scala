package me.supersale.loader

import me.supersale.collections._
import me.supersale.loader

class Loader {
  var countries: Countries = null
  var cities: Cities = null
  var marts: Marts = null

  def loadAll() = {
    countries = new Countries(new loader.CountryLoader().load(this))
    cities = new Cities(new loader.CityLoader().load(this))
    marts = new Marts(new loader.MartLoader().load(this))
  }

  def getCountries = this.countries
}
