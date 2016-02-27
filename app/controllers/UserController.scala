package controllers

import play.api._
import play.api.mvc._
import play.api.i18n._
import play.api.data.Form
import play.api.data.Forms._
import play.api.data.validation.Constraints._
import play.api.libs.json.Json
import models._
import dal._

import scala.concurrent.{ ExecutionContext, Future }

import javax.inject._

class UserController @Inject() (repo: UserRepository, val messagesApi: MessagesApi)
                                 (implicit ec: ExecutionContext) extends Controller with I18nSupport {

  def getUsers = Action.async {
    repo.list().map { users =>
      Ok(Json.toJson(users))
    }
  }

  def deleteUser(id: String) = Action.async {
    val idParsed = id.toInt
    repo.delete(idParsed).map { nbDelete =>
      Ok(nbDelete.toString)
    }
  }

  def updateUser(user: User) = Action.async {
    repo.update(user).map { nbUpdate =>
      Ok(nbUpdate.toString)
    }
  }
}
