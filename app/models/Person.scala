package models

import play.api.libs.json._

case class Person(id: Long, email: String, login: String, password: String)

object Person {
  
  implicit val personFormat = Json.format[Person]
}