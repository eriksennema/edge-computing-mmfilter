//enablePlugins(JavaAppPackaging)

name := "mmfilter"

version := "0.1"

scalaVersion := "2.12.13"

enablePlugins(sbtdocker.DockerPlugin, JavaAppPackaging)

docker / dockerfile := {
  val appDir = stage.value
  val targetDir = "/app"

  new Dockerfile {
    from("openjdk:8-jre")
    entryPoint(s"$targetDir/bin/${executableScriptName.value}")
    copy(appDir, targetDir)
  }
}

docker / buildOptions := BuildOptions(cache = false)

//assemblyMergeStrategy in assembly := {
//  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
//  case x => MergeStrategy.first
//}

// dl4j
libraryDependencies += "org.deeplearning4j" % "deeplearning4j-core" % "1.0.0-beta6"
libraryDependencies += "org.nd4j" % "nd4j-native-platform" % "1.0.0-beta6"

// slf4j
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.1.3" % Runtime

//fork := true

