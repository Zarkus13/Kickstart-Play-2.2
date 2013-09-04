import play.Project._
import net.litola.SassPlugin

name := "KickstartPlay2"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
    jdbc,
    cache,
    "mysql" % "mysql-connector-java" % "5.1.21",
    "org.squeryl" %% "squeryl" % "0.9.5-6",
    "com.typesafe" %% "play-plugins-mailer" % "2.1.0"
)

playScalaSettings ++: Seq(
    SassPlugin.sassOptions := Seq("--compass"),
    SassPlugin.sassEntryPoints <<= (sourceDirectory in Compile)(base => ((base / "assets" ** "*.sass") +++ (base / "assets" ** "*.scss") --- base / "assets" ** "_*")),
    resourceGenerators in Compile <+= SassPlugin.sassWatcher,
    requireJs += "main",
    resolvers += Resolver.sonatypeRepo("snapshots"),
    resolvers += Resolver.url("sbt-plugin-snapshots", new URL("http://repo.scala-sbt.org/scalasbt/sbt-plugin-snapshots/"))(Resolver.ivyStylePatterns)
)