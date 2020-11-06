import sbtcrossproject.CrossPlugin.autoImport.{crossProject, CrossType}

lazy val unique = project.in(file("."))
  .disablePlugins(MimaPlugin)
  .settings(publish / skip := true)
  .aggregate(core.jvm)

lazy val core = crossProject(JVMPlatform)
  .crossType(CrossType.Pure)
  .in(file("core"))
  .settings(commonSettings)
  .settings(
    name := "unique"
  )

lazy val docs = project.in(file("site"))
  .disablePlugins(MimaPlugin)
  .enablePlugins(MicrositesPlugin)
  .enablePlugins(MdocPlugin)
  .settings(commonSettings)
  .settings(skip in publish := true)
  .dependsOn(core.jvm)
  



lazy val contributors = Seq(
  "ChristopherDavenport" -> "Christopher Davenport"
)

val dottyV = "0.27.0-RC1"
// val catsV = "2.3.0-M1"
val catsEffectV = "3.0.0-M2"
val disciplineMunitVersion = "1.0.1"

// General Settings
lazy val commonSettings = Seq(
  organization := "io.chrisdavenport",

  scalaVersion := dottyV,
  // crossScalaVersions := Seq(scalaVersion.value, "2.13.3", "2.12.12"),
  scalacOptions ++= Seq(
    "-language:implicitConversions"
  ),

  libraryDependencies ++= Seq(
    "org.typelevel"               %% "cats-effect"                % catsEffectV,
    // "org.typelevel"               %%% "discipline-specs2"          % disciplineSpecs2V        % Test,
    // "org.typelevel"               %% "cats-laws"                  % catsV                    % Test,
    "org.typelevel" %% "discipline-munit" % disciplineMunitVersion % Test
  ),
  Compile / unmanagedSourceDirectories ++= {
  val major = if (isDotty.value) "-3" else "-2"
    List(CrossType.Pure, CrossType.Full).flatMap(
      _.sharedSrcDir(baseDirectory.value, "main").toList.map(f => file(f.getPath + major))
    )
  }
)

lazy val micrositeSettings = {
  import microsites._
  Seq(
    micrositeName := "unique",
    micrositeDescription := "Functional Unique Values for Scala",
    micrositeAuthor := "Christopher Davenport",
    micrositeGithubOwner := "ChristopherDavenport",
    micrositeGithubRepo := "unique",
    micrositeBaseUrl := "/unique",
    micrositeDocumentationUrl := "https://www.javadoc.io/doc/io.chrisdavenport/unique_2.12",
    micrositeFooterText := None,
    micrositeHighlightTheme := "atom-one-light",
    micrositePalette := Map(
      "brand-primary" -> "#3e5b95",
      "brand-secondary" -> "#294066",
      "brand-tertiary" -> "#2d5799",
      "gray-dark" -> "#49494B",
      "gray" -> "#7B7B7E",
      "gray-light" -> "#E5E5E6",
      "gray-lighter" -> "#F4F3F4",
      "white-color" -> "#FFFFFF"
    ),
    scalacOptions in Tut --= Seq(
      "-Xfatal-warnings",
      "-Ywarn-unused-import",
      "-Ywarn-numeric-widen",
      "-Ywarn-dead-code",
      "-Ywarn-unused:imports",
      "-Xlint:-missing-interpolator,_"
    ),
    micrositePushSiteWith := GitHub4s,
    micrositeGithubToken := sys.env.get("GITHUB_TOKEN"),
    micrositeExtraMdFiles := Map(
        file("CHANGELOG.md")        -> ExtraMdFileConfig("changelog.md", "page", Map("title" -> "changelog", "section" -> "changelog", "position" -> "100")),
        file("CODE_OF_CONDUCT.md")  -> ExtraMdFileConfig("code-of-conduct.md",   "page", Map("title" -> "code of conduct",   "section" -> "code of conduct",   "position" -> "101")),
        file("LICENSE")             -> ExtraMdFileConfig("license.md",   "page", Map("title" -> "license",   "section" -> "license",   "position" -> "102"))
    )
  )
}

inThisBuild(List(
  organization := "io.chrisdavenport",
  developers := List(
    Developer("ChristopherDavenport", "Christopher Davenport", "chris@christopherdavenport.tech", url("https://github.com/ChristopherDavenport"))
  ),

  homepage := Some(url("https://github.com/ChristopherDavenport/unique")),
  licenses += ("MIT", url("http://opensource.org/licenses/MIT")),

  pomIncludeRepository := { _ => false},
  scalacOptions in (Compile, doc) ++= Seq(
      "-groups",
      "-sourcepath", (baseDirectory in LocalRootProject).value.getAbsolutePath,
      "-doc-source-url", "https://github.com/christopherdavenport/unique/blob/v" + version.value + "€{FILE_PATH}.scala"
  ),
  scalacOptions in (Compile, doc) -= "-Xfatal-warnings",
))
