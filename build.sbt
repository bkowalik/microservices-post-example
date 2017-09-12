scalaVersion := "2.12.3"

lazy val akkaHttp = "com.typesafe.akka" %% "akka-http" % "10.0.10"
lazy val akkaSprayJson = "com.typesafe.akka" %% "akka-http-spray-json" % "10.0.10"
lazy val guice = "com.google.inject" % "guice" % "4.1.0"
lazy val inejct = "javax.inject" % "javax.inject" % "1"

lazy val core = (project in file("core"))
  .settings(
    libraryDependencies ++= Seq(akkaHttp, akkaSprayJson, inejct)
  )

lazy val todoApi = (project in file("todoApi"))
  .dependsOn(core)
lazy val todo = (project in file("todo"))
  .dependsOn(todoApi, notificationApi)

lazy val notificationApi = (project in file("notificationApi"))
  .dependsOn(core)
lazy val notification = (project in file("notification"))
  .dependsOn(notificationApi)


lazy val root = (project in file("."))
  .settings(
    libraryDependencies += guice
  )
  .dependsOn(core, notification, notificationApi, todo, todoApi)
  .aggregate(core, notification, notificationApi, todo, todoApi)
