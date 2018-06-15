import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "ch.miyabi",
      scalaVersion := "2.12.6",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "temlate_engine",
    libraryDependencies ++= Seq(
      "org.apache.logging.log4j" %% "log4j-api-scala" % "11.0",
      "org.apache.logging.log4j" % "log4j-api" % "2.11.0",
      "org.apache.logging.log4j" % "log4j-core" % "2.11.0",
      "org.apache.logging.log4j" % "log4j-slf4j-impl" % "2.11.0",
      "com.hubspot.jinjava" % "jinjava" % "2.4.3",
      "nl.big-o" % "liqp" % "0.7.3",
      "org.jtwig" % "jtwig-core" % "5.87.0.RELEASE",
      "io.pebbletemplates" % "pebble" % "2.6.0",
      "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.9.5",
      "org.specs2" %% "specs2-core" % "4.2.0" % Test
    ),
    scalacOptions in Test ++= Seq("-Yrangepos"),
    parallelExecution in Test := false
  )
