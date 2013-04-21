import org.scalatest.matchers.MustMatchers
import org.scalatest.{BeforeAndAfter, WordSpec}

import scala.slick.driver.MySQLDriver.simple._
import scala.slick.session.Database
import Database.threadLocalSession


object Coffees extends Table[(String, Double)]("coffees") {
  def name = column[String]("name", O.PrimaryKey)

  def price = column[Double]("price")

  def * = name ~ price
}

class DatabaseSpec extends WordSpec with MustMatchers with BeforeAndAfter {
  val db = Database.forURL("jdbc:mysql://localhost:3306/test",
    driver = "com.mysql.jdbc.Driver", user = "root", password = "")

  before {
    db withSession {
      try {Coffees.ddl.drop} catch { case e: Exception => }
      Coffees.ddl.create
      Coffees.insertAll(
        ("Colombian", 7.99),
        ("French_Roast", 8.99),
        ("Espresso", 9.99),
        ("Colombian_Decaf", 8.99),
        ("French_Roast_Decaf", 9.99))
    }
  }

  "A Slick Database" must {
    "query data" in {
      db withSession {
        val stuff = for (c <- Coffees) yield (c.name, c.price)
        stuff.list must equal(List(("Colombian", 7.99),
          ("French_Roast", 8.99),
          ("Espresso", 9.99),
          ("Colombian_Decaf", 8.99),
          ("French_Roast_Decaf", 9.99)))
      }
    }
  }
}

