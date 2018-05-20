name := "scala-spring-task"

version := "0.1.0"

organization := "com.yakumobooks"

scalaVersion := "2.11.8"

resolvers ++= Seq(
  "Spring GA Repository"        at "http://repo.spring.io/release",
  "Spring Milestone Repository" at "http://repo.spring.io/milestone"
)

scalacOptions ++= Seq(
  "-encoding", "UTF-8",
  "-deprecation",
  "-unchecked",
  "-feature")

val springBootVersion    = "1.5.13.RELEASE"
libraryDependencies ++= {
  Seq(
    "org.springframework.boot" % "spring-boot-starter" % springBootVersion
  )
}

mainClass in assembly := Some("com.yakumobooks.scheduler.Application")

assemblyMergeStrategy in assembly := {
  case PathList("javax", "servlet", xs @ _*)               => MergeStrategy.first
  case PathList(ps @ _*) if ps.last endsWith ".properties" => MergeStrategy.first
  case PathList(ps @ _*) if ps.last endsWith ".xml"        => MergeStrategy.first
  case PathList(ps @ _*) if ps.last endsWith ".types"      => MergeStrategy.first
  case PathList(ps @ _*) if ps.last endsWith ".class"      => MergeStrategy.first
  case PathList(ps @ _*) if ps.last endsWith ".json"       => MergeStrategy.last
  case PathList(ps @ _*) if ps.last endsWith ".provides"   => MergeStrategy.last
  case PathList(ps @ _*) if ps.last endsWith ".factories"  => MergeStrategy.last
  case "application.conf"                                  => MergeStrategy.concat
  case "unwanted.txt"                                      => MergeStrategy.discard
  case x =>
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
}

// if you remove commentout the following line, this script set the environment properties to the Spring Boot's LaunchScript regardless of the value of the configuration file
assemblyOption in assembly := (assemblyOption in assembly).value.copy(prependShellScript = Some({
  val props = Map[String, String](
    "initInfoProvides"         -> "yakumobooks.com"
      ,"initInfoShortDescription" -> "task scheduler application"
      ,"initInfoDescription"      -> ""
      // ,"confFolder"               -> ""
      // ,"pidFolder"                -> ""
      // ,"logFolder"                -> ""
      ,"mode"                     -> "service"
      // ,"useStartStopDaemon"       -> ""
  )
  val placeholder_regex = "\\{\\{(\\w+)(:.*?)?\\}\\}(?!\\})".r
  val launchScriptsUrl = s"https://raw.githubusercontent.com/spring-projects/spring-boot/v${springBootVersion}/spring-boot-tools/spring-boot-loader-tools/src/main/resources/org/springframework/boot/loader/tools/launch.script"
  scala.io.Source.fromURL(launchScriptsUrl, "UTF-8")
    .getLines
    .toSeq
    .map(placeholder_regex.replaceAllIn(_, m =>
      props.getOrElse(m.group(1), s"${m.group(2).replace("$","\\$").substring(1)}").stripLineEnd))

}))

//assemblyJarName in assembly := s"${name.value}-${version.value}.jar"
assemblyJarName in assembly := s"${name.value}.jar"
