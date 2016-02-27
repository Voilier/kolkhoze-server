package controllers

import javax.inject._

import dal._
import io.swagger.annotations.Api
import play.api.i18n._
import play.api.libs.json.Json
import play.api.mvc._

import scala.concurrent.ExecutionContext

@Api(value = "/item", description = "Operations about items")
class ItemController @Inject() (repo: ItemRepository, val messagesApi: MessagesApi)
                                 (implicit ec: ExecutionContext) extends Controller with I18nSupport {
  def getItems = Action.async {
  	repo.list().map { item =>
      Ok(Json.toJson(item))
    }
  }
}
