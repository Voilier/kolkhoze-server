package models

/**
  * Created by thomas on 26/02/16.
  */

import play.api.libs.json._

case class ItemLent(id: Long, itemId: Long, ownerId: Long, lentId: Long)


object ItemLent {
  implicit val itemFormat = Json.format[ItemLent]
}
