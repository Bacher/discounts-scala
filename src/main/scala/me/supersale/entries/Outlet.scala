package me.supersale.entries

import me.supersale._

class Outlet(
  var id: Long,
  val title: String
) {

}

class MartOutlet(id: Long, title: String, val mart: Mart)
  extends Outlet(id, title) {

}

class OutstandOutlet(id: Long, title: String, val address: String, val position: Position)
  extends Outlet(id, title) {

}
