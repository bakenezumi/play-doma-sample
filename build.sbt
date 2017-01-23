lazy val root = (project in file(".")).enablePlugins(PlayScala).settings(
  inThisBuild(List(
    scalaVersion := "2.11.7",
    version := "1.0-SNAPSHOT"
  )),
  name := "play-doma",
  libraryDependencies ++= Seq(
    cache,
    ws,
    evolutions,
    "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test,
    "mysql" % "mysql-connector-java" % "5.1.26"
  ),
  unmanagedSourceDirectories in Compile <+= classDirectory in Compile in dao
) dependsOn (dao, doma) aggregate (dao, doma)

lazy val processAnnotations = taskKey[Unit]("Process annotations")

val lastModifiedDaoMap = scala.collection.mutable.Map[String, Long]()

lazy val dao = project.settings(
  processAnnotations in Compile := {
    val classes = (unmanagedSources in Compile).value.filter { f =>
      f.getPath.endsWith("scala") && lastModifiedDaoMap.put(f.getPath, f.lastModified) != Some(f.lastModified)
    }
    if (classes.nonEmpty) {
      val log = streams.value.log
      log.info("Processing annotations ...")
      val cutSize = (scalaSource in Compile).value.getPath.size + 1
      val classesToProcess = classes.map(_.getPath.substring(cutSize).replaceFirst("\\.scala", "").replaceAll("\\\\", ".")).mkString(" ")
      val classpath =  (((dependencyClasspath in Compile).value.files) mkString ";") + ";" + (classDirectory in Compile).value.toString
      val destinationDirectory = (classDirectory in Compile).value
      val command = s"javac -cp $classpath -proc:only -XprintRounds -d $destinationDirectory $classesToProcess"
      executeCommand(command, "Failed to process annotations.", log)
      log.info("Done processing annotations.")
    }
  },
  copyResources in Compile := (Def.taskDyn {
    val copy = (copyResources in Compile).value
    Def.task {
      val proc = (processAnnotations in Compile).value
      copy
    }
  }).value
) dependsOn doma

lazy val doma = project.settings(
  libraryDependencies ++= Seq(
    jdbc,
    "org.seasar.doma" % "doma" % "2.14.0"
  )
)

def executeCommand(command: String, errorMessage: => String, log: Logger) {
  val result = command !

  if (result != 0) {
    log.error(errorMessage)
    sys.error("Failed running command: " + command)
  }
}
