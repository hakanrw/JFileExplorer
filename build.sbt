lazy val root = (project in file(".")).
  settings(
    name := "JFileExplorer",
    autoScalaLibrary := false,
    libraryDependencies ++= Seq(
      "junit" % "junit" % "4.12" % "test",
      "com.novocode" % "junit-interface" % "0.11" % "test"
    ),

    console / initialCommands := "import dev.candar.swing._; JFileExplorer.main(Array[String]())"
  )
