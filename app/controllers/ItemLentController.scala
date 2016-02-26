package controllers

import javax.inject._

import dal._
import play.api.i18n._
import play.api.libs.json.Json
import play.api.mvc._

import scala.concurrent.ExecutionContext

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
