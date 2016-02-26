package models

/**
  * Created by thomas on 26/02/16.
  */

import play.api.libs.json._

case class Item(id: Long, name: String, itemType: Int, ownerId:Long, lentId: Long)

object Item {

  implicit val itemFormat = Json.format[Item]
}
