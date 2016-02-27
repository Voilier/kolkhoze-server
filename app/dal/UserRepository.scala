package dal

import javax.inject.{ Inject, Singleton }
import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile

import models.{Item, User}

import scala.concurrent.{ Future, ExecutionContext }

/**
  * Created by sandman on 2/26/16.
  */
@Singleton
class UserRepository @Inject() (dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import driver.api._

  private class UserTable(tag: Tag) extends Table[User](tag, "user") {

    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def email = column[String]("email")
    def login = column[String]("login")
    def password = column[String]("password")

    def * = (id, email, login, password) <> ((User.apply _).tupled, User.unapply)
  }

  private val users = TableQuery[UserTable]

  def create(email: String, login: String, password: String): Future[User] = db.run {
    (users.map(p => (p.email, p.login, p.password))
      returning users.map(_.id)
      into ((i, id) => User(id, i._1, i._2, i._3))
      ) += (email, login, password)
  }

  // TODO we can set this in a trait
  def update(user: User) = db.run {
    users.filter(_.id === user.id).update(user)
  }

  def delete(id: Long) = db.run {
    users.filter(_.id === id).delete
  }

  def list(): Future[Seq[User]] = db.run {
    users.result
  }
}
