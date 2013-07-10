organization := "tv.cntt"

name := "rhinocoffeescript"

version := "1.6.3"

javacOptions ++= Seq(
  "-source",
  "1.5"
)

libraryDependencies += "org.mozilla" % "rhino" % "1.7R4"

// Remove Scala dependency
autoScalaLibrary := false

// Remove Scala version in output paths and artifacts
crossPaths := false

// Skip API doc generation to speedup "publish-local" while developing.
// Comment out this line when publishing to Sonatype.
publishArtifact in (Compile, packageDoc) := false
