name := "Assignment 4"

version := "0.1.0"

scalaVersion := "2.9.1"

organization := "me.arrdem"

scalacOptions ++= Seq("-unchecked", "-deprecation")

mainClass in (Compile, run) := Some("me.arrdem.garden.Runner")
