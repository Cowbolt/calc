package ardoq

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import io.circe.generic.auto._
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport

object CalcRoutes extends ArithmeticParser with FailFastCirceSupport {

  case class Expression(expression: String)
  case class Value(value: String)

  lazy val calcRoutes: Route =
    get {
      pathSingleSlash {
        getFromResource("index.html")
      } ~
        // All other get requests attempt to fetch resources
        getFromResourceDirectory("")
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
