name := "Discounts"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http-core" % "2.4.7",
  "com.typesafe.akka" %% "akka-http-experimental" % "2.4.7",
  "org.scalikejdbc" %% "scalikejdbc" % "2.4.1",
  "mysql" % "mysql-connector-java" % "5.1.24",
  //"ch.qos.logback" % "logback-classic" % "1.1.7",
  "io.spray" %% "spray-json" % "1.3.2"
)