package controllers

import javax.inject._

import dal._
import io.swagger.annotations.Api
import play.api.i18n._
import play.api.libs.json.Json
import play.api.mvc._

import scala.concurrent.ExecutionContext

@Api(value = "/itemLent", description = "Operations about lent items")
class ItemLentController @Inject()(repo: ItemLentRepository, val messagesApi: MessagesApi)
                                  (implicit ec: ExecutionContext) extends Controller with I18nSupport{


  /**
   * A REST endpoint that gets all the people as JSON.
   */
  def getItemsLent = Action.async {
  	repo.list().map { item =>
      Ok(Json.toJson(item))
    }
  }
}
