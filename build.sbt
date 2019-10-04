lazy val akkaHttpVersion = "10.1.10"
lazy val akkaVersion    = "2.5.23"
lazy val circeVersion = "0.10.0"

lazy val root = (project in file(".")).
settings(
    inThisBuild(List(
        organization    := "ardoq",
        scalaVersion    := "2.12.8"
        )),
    name := "calc",
    libraryDependencies ++= Seq(
      "com.typesafe.akka"      %% "akka-http"                % "10.1.10" ,
      "com.typesafe.akka"      %% "akka-stream"              % "2.5.23",
      "org.scalatest"          %% "scalatest"                % "3.0.5"      % Test,
      "io.circe"               %% "circe-core"               % circeVersion,
      "io.circe"               %% "circe-generic"            % circeVersion,
      "de.heikoseeberger"      %% "akka-http-circe"          % "1.25.2",
      "org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.2"
      )
    )
