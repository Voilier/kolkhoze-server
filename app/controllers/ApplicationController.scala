package controllers

import play.api.mvc.{Controller, Action}

/**
  * Created by thomas on 27/02/16.
  */
  class ApplicationController extends Controller {

    def index = Action {
      request =>
        Ok(views.html.index())
    }

  }
