package dal

import javax.inject.{Inject, Singleton}

import models.ItemLent
import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}


@Singleton
class ItemLentRepository @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  // We want the JdbcProfile for this provider
  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  // These imports are important, the first one brings db into scope, which will let you do the actual db operations.
  // The second one brings the Slick DSL into scope, which lets you define the table and other queries.
  import dbConfig._
  import driver.api._

  //defining table
  private class ItemLentTable(tag: Tag) extends Table[ItemLent](tag, "itemLent") {

    /** The ID column, which is the primary key, and auto incremented */
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def itemId = column[Long]("itemId")
    def ownerId = column[Long]("ownerUserId")
    def lentId = column[Long]("lentUserId")

    //projection
    def * = (id, itemId, ownerId, lentId) <> ((ItemLent.apply _).tupled, ItemLent.unapply)
  }

  //starting point
  private val itemsLent = TableQuery[ItemLentTable]

  //async
  def create(itemId: Long, ownerId:Long, lentId: Long): Future[ItemLent] = db.run {
    // We create a projection of just the name and age columns, since we're not inserting a value for the id column
    (itemsLent.map(p => (p.itemId, p.ownerId, p.lentId))
      // Now define it to return the id, because we want to know what id was generated for the person
      returning itemsLent.map(_.id)
      // And we define a transformation for the returned value, which combines our original parameters with the
      // returned id
      into ((ite, id) => ItemLent(id, ite._1, ite._2, ite._3))
    // And finally, insert the person into the database
    ) += (itemId, ownerId, lentId)
  }

  def list(): Future[Seq[ItemLent]] = db.run {
    itemsLent.result
  }
}
