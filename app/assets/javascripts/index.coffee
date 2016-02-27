$ ->
  $.get "/person/get", (persons) ->
    $.each persons, (index, person) ->
      email = $("<div>").addClass("email").text person.email
      login = $("<div>").addClass("login").text person.login
      password = $("<div>").addClass("password").text person.password
      $("#persons").append $("<li>").append(email).append(login).append(password)

