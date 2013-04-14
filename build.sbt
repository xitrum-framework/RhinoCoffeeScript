organization := "tv.cntt"

name := "rhinocoffeescript"

version := "1.6.2-SNAPSHOT"

libraryDependencies += "org.mozilla" % "rhino" % "1.7R4"

// Remove Scala dependency
autoScalaLibrary := false

// Remove Scala version in output paths and artifacts
crossPaths := false

// Skip API doc generation
publishArtifact in (Compile, packageDoc) := false
