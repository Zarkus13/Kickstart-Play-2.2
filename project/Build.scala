//import net.litola.SassPlugin
//import sbt._
//import Keys._
//import play.Project._
//
//object ApplicationBuild extends Build {
//
//    val appName = "always-geek-scala"
//    val appVersion = "1.0-SNAPSHOT"
//
//    val appDependencies = Seq(
//        // Add your project dependencies here,
//        jdbc,
//        "mysql" % "mysql-connector-java" % "5.1.21",
//        "org.squeryl" %% "squeryl" % "0.9.5-6",
//        "com.typesafe" %% "play-plugins-mailer" % "2.1.0"
//    )
//
//
//    val main = play.Project(appName, appVersion, appDependencies).settings(
//        //        routesImport += "se.radley.plugin.salat.Binders._",
//        //        templatesImport += "org.bson.types.ObjectId",
//        SassPlugin.sassOptions := Seq("--compass"),
//        SassPlugin.sassEntryPoints <<= (sourceDirectory in Compile)(base => ((base / "assets" ** "*.sass") +++ (base / "assets" ** "*.scss") --- base / "assets" ** "_*")),
//        resourceGenerators in Compile <+= SassPlugin.sassWatcher,
//        requireJs += "main",
//        resolvers += Resolver.sonatypeRepo("snapshots"),
//        resolvers += Resolver.url("sbt-plugin-snapshots", new URL("http://repo.scala-sbt.org/scalasbt/sbt-plugin-snapshots/"))(Resolver.ivyStylePatterns)
//    )
//
//}
