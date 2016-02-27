package models

import play.api.libs.json._

case class User(id: Long, email: String, login: String, password: String)

object User {
  implicit val userFormat = Json.format[User]
}