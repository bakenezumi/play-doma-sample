lazy val root = (project in file(".")).enablePlugins(PlayScala).settings(
  inThisBuild(List(
    scalaVersion := "2.12.2",
    version := "0.1.0"
  )),
  name := "play-doma-sample",
  libraryDependencies ++= Seq(
    guice,
    ws,
    evolutions,
    "com.h2database" % "h2" % "1.4.193",
    "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.0" % Test
  ),
  unmanagedSourceDirectories in Compile += (classDirectory in Compile in dao).value
) dependsOn (dao, domala) aggregate (dao, domala)

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
      val separator = System.getProperties.getProperty("path.separator")
      val classesToProcess = classes.map(_.getPath.substring(cutSize).replaceFirst("\\.scala", "").replaceAll("[\\\\/]", ".")).mkString(" ")
      val classpath =  (((dependencyClasspath in Compile).value.files) mkString separator) + separator + (classDirectory in Compile).value.toString
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
) dependsOn domala

lazy val domala = project.settings(
  libraryDependencies ++= Seq(
    jdbc,
    "org.seasar.doma" % "doma" % "2.16.1"
  )
)

def executeCommand(command: String, errorMessage: => String, log: Logger) {
  val result = command !

  if (result != 0) {
    log.error(errorMessage)
    sys.error("Failed running command: " + command)
  }
}
