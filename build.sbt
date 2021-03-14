name := "mmfilter"

version := "0.1"

// Scorch & Numsca dependency
libraryDependencies += "be.botkop" %% "scorch" % "0.1.0"
libraryDependencies += "be.botkop" %% "numsca" % "0.1.5"

// dl4j
libraryDependencies += "org.deeplearning4j" % "deeplearning4j-core" % "1.0.0-beta2"
libraryDependencies += "org.nd4j" % "nd4j-native-platform" % "1.0.0-beta2"

fork := true

scalaVersion := "2.12.1"