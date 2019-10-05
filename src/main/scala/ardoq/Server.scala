package ardoq

import akka.actor.ActorSystem
import scala.concurrent.ExecutionContext
import akka.stream.ActorMaterializer
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import scala.io.StdIn
import com.typesafe.config.{ Config, ConfigFactory }

import scala.concurrent.{ Await, ExecutionContext, Future }
import scala.concurrent.duration.Duration
import scala.util.{ Failure, Success }

import akka.actor.{ ActorRef, ActorSystem }
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer

object Server extends App {
  import CalcRoutes._

  implicit val system: ActorSystem = ActorSystem("resttest")
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val executor: ExecutionContext = system.dispatcher

  val port: Int = sys.env.getOrElse("PORT", "8080").toInt
  val bindingFuture = Http().bindAndHandle(calcRoutes, "0.0.0.0", port)

  println(s"Server online!")
}
