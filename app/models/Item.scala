package models

import play.api.libs.json._

/**
  * Created by thomas on 26/02/16.
  */
case class Item(id: Long, name: String, itemType: Int, ownerId:Long, lentId: Long)

object Item {
  implicit val itemFormat = Json.format[Item]
}
