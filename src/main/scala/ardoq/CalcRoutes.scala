package ardoq

import akka.actor.{ ActorRef, ActorSystem }
import akka.event.Logging

import scala.concurrent.duration._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route

import io.circe._, io.circe.generic.auto._

import akka.http.scaladsl.model._
import akka.http.scaladsl.model.ContentTypes._
import akka.http.scaladsl.model.headers.`Content-Type`

// import com.typesafe.config.{Config, ConfigFactory}
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport

object CalcRoutes extends ArithmeticParser with FailFastCirceSupport {

  case class Expression(expression: String)
  case class Value(value: String)

  lazy val calcRoutes: Route =
    get {
      pathSingleSlash {
        complete {
          HttpEntity(ContentTypes.`text/html(UTF-8)`, "Hello, world!")
        }
      }
    } ~
      post {
        path("evaluate")
        entity(as[Expression]) { e =>
          complete {
            // Return result if expression is valid. Otherwise, return "Error".
            try {
              Value(parse(e.expression).toString)
            } catch {
              case e: RuntimeException => {
                println(e)
                Value("Error")
              }
            }
          }
        }
      }
}
