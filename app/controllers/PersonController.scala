package controllers

import javax.inject._

import dal._
import io.swagger.annotations._
import play.api.i18n._
import play.api.libs.json.Json
import play.api.mvc._

import scala.concurrent.ExecutionContext

@Api(value = "/person", description = "Operations about persons")
class PersonController @Inject() (repo: PersonRepository, val messagesApi: MessagesApi)
                                 (implicit ec: ExecutionContext) extends Controller with I18nSupport{


//  @ApiOperation(nickname = "addPerson", value = "Adding a person", notes = "Add a person", response = classOf[models.Person], httpMethod = "GET")
//  def addPerson = Action.async { implicit request =>
//    // Bind the form first, then fold the result, passing a function to handle errors, and a function to handle succes.
//    personForm.bindFromRequest.fold(
//      // The error function. We return the index page with the error form, which will render the errors.
//      // We also wrap the result in a successful future, since this action is synchronous, but we're required to return
//      // a future because the person creation function returns a future.
//      errorForm => {
//        Future.successful(Ok(views.html.index(errorForm)))
//      },
//      // There were no errors in the from, so create the person.
//      person => {
//        repo.create(person.email, person.login, person.password).map { _ =>
//          // If successful, we simply redirect to the index page.
//          Redirect(routes.PersonController.getPersons())
//        }
//      }
//    )
//  }

  @ApiOperation(nickname = "getPersons", value = "Get list of all persons", notes = "", response = classOf[models.Person], httpMethod = "GET")
  def getPersons = Action.async {
  	repo.list().map { people =>
      Ok(Json.toJson(people))
    }
  }
}
