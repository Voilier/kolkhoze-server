package controllers

import javax.inject.Inject

import dal.UserRepository
import models.forms.{CreateLoginForm, CreateSignupForm}
import play.api.data.Form
import play.api.data.Forms._
import play.api.data.validation.Constraints._
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc.Controller
import play.api.mvc._

import scala.concurrent.{Future, ExecutionContext}

/**
  * Created by sandman on 2/25/16.
  */
class HomepageController @Inject() (repo: UserRepository, val messagesApi: MessagesApi)
                                   (implicit ec: ExecutionContext) extends Controller with I18nSupport {
  val loginForm: Form[CreateLoginForm] = Form {
    mapping(
      "login" -> nonEmptyText,
      "password" -> nonEmptyText
    )(CreateLoginForm.apply)(CreateLoginForm.unapply)
  }
  val signupForm: Form[CreateSignupForm] = Form {
    mapping(
      "login" -> nonEmptyText,
      "email" -> nonEmptyText,
      "password" -> nonEmptyText
    )(CreateSignupForm.apply)(CreateSignupForm.unapply)
  }

  def simpleSignup = Action.async { implicit request =>
    signupForm.bindFromRequest.fold(
      errorForm => {
        Future.successful(Ok(views.html.signup(errorForm)))
      },
      user => {
        repo.create(user.email, user.login, user.password).map { _ =>
          Redirect(routes.ItemController.getItems)
        }
      }
    )
  }

  def simpleLogin = Action.async { implicit request =>
    loginForm.bindFromRequest.fold(
      errorForm => {
        Future.successful(Ok(views.html.login(errorForm)))
      },
      user => {
        repo.list().map(users => users.find(_ == user)).map { _ =>
          Redirect(routes.ItemController.getItems)
        }
      }
    )
  }

  def api = Action {
    Ok(views.html.api())
  }

  def login = Action {
    // TODO we'll add credentials and auth later
    Ok(views.html.login(loginForm))
  }
  def signup = Action {
    Ok(views.html.signup(signupForm))
  }
}
