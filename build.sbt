organization := "tv.cntt"

name := "rhinocoffeescript"

version := "1.7.1"

// Remove Scala dependency
autoScalaLibrary := false

// Remove Scala version in output paths and artifacts
crossPaths := false

javacOptions ++= Seq("-source", "1.5", "-target", "1.5", "-Xlint:deprecation")

javacOptions in doc := Seq("-source", "1.5")

libraryDependencies += "org.mozilla" % "rhino" % "1.7R4"

//------------------------------------------------------------------------------

// Skip API doc generation to speedup "publish-local" while developing.
// Comment out this line when publishing to Sonatype.
publishArtifact in (Compile, packageDoc) := false
