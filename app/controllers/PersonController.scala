package controllers

import io.swagger.annotations._
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

@Api(value = "/person", description = "Operations about persons")
class PersonController @Inject() (repo: PersonRepository, val messagesApi: MessagesApi)
                                 (implicit ec: ExecutionContext) extends Controller with I18nSupport{

  /**
   * The mapping for the person form.
   */
  val personForm: Form[CreatePersonForm] = Form {
    mapping(
      "email" -> nonEmptyText,
      "login" -> nonEmptyText,
      "password" -> nonEmptyText
    )(CreatePersonForm.apply)(CreatePersonForm.unapply)
  }

  def index = Action {
    Ok(views.html.index(personForm))
  }

  @ApiOperation(nickname = "addPerson", value = "Adding a person", notes = "Add a person", response = classOf[models.Person], httpMethod = "GET")
  def addPerson = Action.async { implicit request =>
    // Bind the form first, then fold the result, passing a function to handle errors, and a function to handle succes.
    personForm.bindFromRequest.fold(
      // The error function. We return the index page with the error form, which will render the errors.
      // We also wrap the result in a successful future, since this action is synchronous, but we're required to return
      // a future because the person creation function returns a future.
      errorForm => {
        Future.successful(Ok(views.html.index(errorForm)))
      },
      // There were no errors in the from, so create the person.
      person => {
        repo.create(person.email, person.login, person.password).map { _ =>
          // If successful, we simply redirect to the index page.
          Redirect(routes.PersonController.getPersons())
        }
      }
    )
  }

  @ApiOperation(nickname = "getPersons", value = "Get list of all persons", notes = "", response = classOf[models.Person], httpMethod = "GET")
  def getPersons = Action.async {
  	repo.list().map { people =>
      Ok(Json.toJson(people))
    }
  }
}

/**
 * The create person form.
 *
 * Generally for forms, you should define separate objects to your models, since forms very often need to present data
 * in a different way to your models.  In this case, it doesn't make sense to have an id parameter in the form, since
 * that is generated once it's created.
 */
case class CreatePersonForm(email: String, login: String, password: String)
