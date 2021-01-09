import sbtcrossproject.CrossPlugin.autoImport.{crossProject, CrossType}

ThisBuild / organization := "org.typelevel"
ThisBuild / baseVersion := "2.1"

ThisBuild / publishGithubUser := "christopherdavenport"
ThisBuild / publishFullName := "Christopher Davenport"

lazy val unique = project.in(file("."))
  .disablePlugins(MimaPlugin)
  .settings(commonSettings, releaseSettings, publish / skip := true)
  .aggregate(coreJVM, coreJS)

lazy val core = crossProject(JSPlatform, JVMPlatform)
  .crossType(CrossType.Pure)
  .in(file("core"))
  .settings(commonSettings, releaseSettings)
  .settings(
    name := "unique"
  )
  .jsSettings(scalaJSLinkerConfig ~= (_.withModuleKind(ModuleKind.CommonJSModule)))
  .jsSettings(crossScalaVersions := crossScalaVersions.value.filterNot(_.startsWith("3.0.0-M1")))

lazy val docs = project.in(file("docs"))
  .disablePlugins(MimaPlugin)
  .enablePlugins(MicrositesPlugin)
  .enablePlugins(TutPlugin)
  .settings(commonSettings, releaseSettings, micrositeSettings, publish / skip := true)
  .dependsOn(coreJVM)
  
lazy val coreJVM = core.jvm
lazy val coreJS = core.js


val catsV = "2.3.0"
val catsEffectV = "3.0.0-M4"
val disciplineMunitV = "1.0.3"
val munitCatsEffectV = "0.11.0"

lazy val contributors = Seq(
  "ChristopherDavenport" -> "Christopher Davenport"
)

// General Settings
lazy val commonSettings = Seq(
  crossScalaVersions := Seq("3.0.0-M2", "3.0.0-M1", "2.13.3", "2.12.12"),
  scalaVersion := crossScalaVersions.value.filter(_.startsWith("2.")).head,
  libraryDependencies ++= Seq(
    "org.typelevel"               %%% "cats-core"                  % catsV,
    "org.typelevel"               %%% "cats-effect"                % catsEffectV,
    "org.typelevel"               %%% "discipline-munit"           % disciplineMunitV         % Test,
    "org.typelevel"               %%% "munit-cats-effect-3"        % munitCatsEffectV         % Test,
    "org.typelevel"               %%% "cats-laws"                  % catsV                    % Test,
  ),
)

lazy val releaseSettings = {
  Seq(
    scmInfo := Some(
      ScmInfo(
        url("https://github.com/typelevel/unique"),
        "git@github.com:typelevel/unique.git"
      )
    ),
    homepage := Some(url("https://github.com/typelevel/unique")),
    licenses := List("MIT" -> url("http://opensource.org/licenses/MIT")),
    pomIncludeRepository := { _ => false },
  )
}

lazy val micrositeSettings = {
  import microsites._
  Seq(
    micrositeName := "unique",
    micrositeDescription := "Functional Unique Values for Scala",
    micrositeAuthor := "Christopher Davenport",
    micrositeGithubOwner := "ChristopherDavenport",
    micrositeGithubRepo := "unique",
    micrositeBaseUrl := "/unique",
    micrositeDocumentationUrl := "https://www.javadoc.io/doc/org.typelevel/unique_2.13",
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
    libraryDependencies += "com.47deg" %% "github4s" % "0.27.1",
    micrositePushSiteWith := GitHub4s,
    micrositeGithubToken := sys.env.get("GITHUB_TOKEN"),
    micrositeExtraMdFiles := Map(
        file("CHANGELOG.md")        -> ExtraMdFileConfig("changelog.md", "page", Map("title" -> "changelog", "section" -> "changelog", "position" -> "100")),
        file("CODE_OF_CONDUCT.md")  -> ExtraMdFileConfig("code-of-conduct.md",   "page", Map("title" -> "code of conduct",   "section" -> "code of conduct",   "position" -> "101")),
        file("LICENSE")             -> ExtraMdFileConfig("license.md",   "page", Map("title" -> "license",   "section" -> "license",   "position" -> "102"))
    )
  )
}
