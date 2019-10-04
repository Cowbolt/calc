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

  // val config: Config = ConfigFactory.load("application.conf")
  // val host = config.getString("dev.server.host")
  // val port = config.getInt("dev.server.port")

  implicit val system: ActorSystem = ActorSystem("resttest")
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val executor: ExecutionContext = system.dispatcher

  val bindingFuture = Http().bindAndHandle(calcRoutes, "localhost", 8080)

  println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
  StdIn.readLine() // let it run until user presses return
  bindingFuture
    .flatMap(_.unbind()) // trigger unbinding from the port
    .onComplete(_ => system.terminate()) // and shutdown when done
}
