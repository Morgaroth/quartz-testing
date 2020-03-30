name := "quarts-tests"

version := "0.1"

scalaVersion := "2.12.11"

libraryDependencies ++= Seq(
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "org.quartz-scheduler" % "quartz" % "2.3.2",
  )