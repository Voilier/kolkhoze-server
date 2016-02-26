package dal

import javax.inject.{Inject, Singleton}

import models.Item
import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}


@Singleton
class ItemRepository @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  // We want the JdbcProfile for this provider
  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  // These imports are important, the first one brings db into scope, which will let you do the actual db operations.
  // The second one brings the Slick DSL into scope, which lets you define the table and other queries.
  import dbConfig._
  import driver.api._

  //defining table
  private class ItemTable(tag: Tag) extends Table[Item](tag, "item") {

    /** The ID column, which is the primary key, and auto incremented */
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

    //name: String, itemType: Int, lentId: Long)
    def name = column[String]("name")
    def itemType = column[Int]("itemType")
    def ownerId = column[Long]("ownerId")
    def lentId = column[Long]("lentId")

    //projection
    def * = (id, name, itemType, ownerId, lentId) <> ((Item.apply _).tupled, Item.unapply)
  }

  //starting point
  private val items = TableQuery[ItemTable]

  //async
  def create(name: String, itemType: Int, ownerId:Long, lentId: Long): Future[Item] = db.run {
    // We create a projection of just the name and age columns, since we're not inserting a value for the id column
    (items.map(p => (p.name, p.itemType, p.ownerId, p.lentId))
      // Now define it to return the id, because we want to know what id was generated for the person
      returning items.map(_.id)
      // And we define a transformation for the returned value, which combines our original parameters with the
      // returned id
      into ((ite, id) => Item(id, ite._1, ite._2, ite._3, ite._4))
    // And finally, insert the person into the database
    ) += (name, itemType, ownerId, lentId)
  }

  def list(): Future[Seq[Item]] = db.run {
    items.result
  }
}
