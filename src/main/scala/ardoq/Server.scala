package ardoq

import akka.actor.ActorSystem
import scala.concurrent.ExecutionContext
import akka.stream.ActorMaterializer
import akka.http.scaladsl.Http

import scala.concurrent.ExecutionContext

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
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
