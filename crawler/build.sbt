val scala3Version = "3.3.0"
val zioVersion = "2.0.15"
Global / onChangedBuildSource := ReloadOnSourceChanges


lazy val root = project
  .in(file("."))
  .settings(
    name := "WebCrawler",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

    libraryDependencies += "dev.zio" %% "zio" % zioVersion,
    libraryDependencies += "dev.zio" %% "zio-nio" % "2.0.2",
    libraryDependencies += "dev.zio" %% "zio-http" % "3.0.0-RC4"
    

  )
