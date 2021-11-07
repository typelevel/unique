import sbtcrossproject.CrossPlugin.autoImport.{crossProject, CrossType}

val Scala212 = "2.12.15"
val Scala213 = "2.13.7"
val Scala3 = "3.0.2"

ThisBuild / organization := "org.typelevel"
ThisBuild / baseVersion := "2.1"
ThisBuild / crossScalaVersions := Seq(Scala212, Scala213, Scala3)
ThisBuild / scalaVersion := crossScalaVersions.value.filter(_.startsWith("2.")).last

ThisBuild / publishGithubUser := "christopherdavenport"
ThisBuild / publishFullName := "Christopher Davenport"

ThisBuild / spiewakMainBranches := Seq("main", "series/2.x")

enablePlugins(SonatypeCiReleasePlugin)

lazy val unique = project
  .in(file("."))
  .enablePlugins(NoPublishPlugin)
  .aggregate(coreJVM, coreJS)
  .settings(commonSettings, releaseSettings, publish / skip := true)

lazy val core = crossProject(JSPlatform, JVMPlatform)
  .crossType(CrossType.Pure)
  .in(file("core"))
  .settings(commonSettings, releaseSettings)
  .settings(
    name := "unique"
  )
  .jsSettings(scalaJSLinkerConfig ~= (_.withModuleKind(ModuleKind.CommonJSModule)))

lazy val docs = project
  .in(file("docs"))
  .disablePlugins(MimaPlugin, NoPublishPlugin)
  .enablePlugins(MicrositesPlugin)
  .settings(
    commonSettings,
    releaseSettings,
    micrositeSettings,
    publish / skip := true,
    githubWorkflowArtifactUpload := false
  )
  .dependsOn(coreJVM)

lazy val coreJVM = core.jvm
lazy val coreJS = core.js

val catsV = "2.6.1"
val catsEffectV = "2.5.4"
val disciplineMunitV = "1.0.9"
val munitCatsEffectV = "1.0.6"
val kindProjectorV = "0.13.2"

lazy val contributors = Seq(
  "ChristopherDavenport" -> "Christopher Davenport"
)

// General Settings
lazy val commonSettings = Seq(
  libraryDependencies ++= (
    if (ScalaArtifacts.isScala3(scalaVersion.value)) Nil
    else
      Seq(
        compilerPlugin(("org.typelevel" % "kind-projector" % kindProjectorV).cross(CrossVersion.full))
      )
  ),
  libraryDependencies ++= Seq(
    "org.typelevel" %%% "cats-core" % catsV,
    "org.typelevel" %%% "cats-effect" % catsEffectV,
    "org.typelevel" %%% "discipline-munit" % disciplineMunitV % Test,
    "org.typelevel" %%% "munit-cats-effect-2" % munitCatsEffectV % Test,
    "org.typelevel" %%% "cats-laws" % catsV % Test
  ),
  testFrameworks += new TestFramework("munit.Framework")
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
    pomIncludeRepository := { _ => false }
  )
}

lazy val micrositeSettings = {
  import microsites._
  Seq(
    micrositeName := "unique",
    micrositeDescription := "Functional Unique Values for Scala",
    micrositeAuthor := "Typelevel",
    micrositeGithubOwner := "typelevel",
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
    libraryDependencies += "com.47deg" %% "github4s" % "0.27.1",
    micrositePushSiteWith := GitHub4s,
    micrositeGithubToken := sys.env.get("GITHUB_TOKEN"),
    micrositeExtraMdFiles := Map(
      file("CHANGELOG.md") -> ExtraMdFileConfig("changelog.md",
                                                "page",
                                                Map("title" -> "changelog",
                                                    "section" -> "changelog",
                                                    "position" -> "100"
                                                )
      ),
      file("CODE_OF_CONDUCT.md") -> ExtraMdFileConfig("code-of-conduct.md",
                                                      "page",
                                                      Map("title" -> "code of conduct",
                                                          "section" -> "code of conduct",
                                                          "position" -> "101"
                                                      )
      ),
      file("LICENSE") -> ExtraMdFileConfig("license.md",
                                           "page",
                                           Map("title" -> "license", "section" -> "license", "position" -> "102")
      )
    ),
    mdocIn := (Compile / sourceDirectory).value / "mdoc"
  )
}

val isScala213Cond = s"matrix.scala == '$Scala213'"

ThisBuild / githubWorkflowBuildPreamble ++= Seq(
  WorkflowStep.Use(UseRef.Public("ruby", "setup-ruby", "v1"),
                   params = Map("ruby-version" -> "2.7"),
                   cond = Some(isScala213Cond)
  ),
  WorkflowStep.Run(List("gem install bundler"), cond = Some(isScala213Cond)),
  WorkflowStep.Run(List("bundle install --gemfile=docs/Gemfile"), cond = Some(isScala213Cond))
)

ThisBuild / githubWorkflowTargetBranches := List("*", "series/*")

ThisBuild / githubWorkflowEnv += ("JABBA_INDEX" -> "https://github.com/typelevel/jdk-index/raw/main/index.json")
ThisBuild / githubWorkflowJavaVersions := Seq("adoptium@8", "adoptium@11", "adoptium@17")
ThisBuild / githubWorkflowBuild := Seq(
  WorkflowStep
    .Sbt(
      List("fmtCheck"),
      name = Some("Check formatting")
    ),
  WorkflowStep.Sbt(List("mimaReportBinaryIssues"), name = Some("Check binary issues")),
  WorkflowStep.Sbt(List("Test/compile"), name = Some("Compile")),
  WorkflowStep.Sbt(List("test"), name = Some("Run tests")),
  WorkflowStep.Sbt(List("docs/makeMicrosite"), name = Some("Build the microsite"), cond = Some(isScala213Cond))
)

ThisBuild / githubWorkflowPublish := Seq(
  WorkflowStep.Sbt(List("release"), name = Some("Release")),
  WorkflowStep.Sbt(List("docs/publishMicrosite"), name = Some(s"Publish the microsite"))
)

ThisBuild / versionIntroduced := Map(
  "2.12" -> "2.1.1",
  "2.13" -> "2.1.1",
  "3.0.0-M3" -> "2.1.1",
  "3.0.0-RC1" -> "2.1.1",
  "3.0.0-RC2" -> "2.1.3",
  "3.0.0-RC3" -> "2.1.4"
)

// Scalafmt
addCommandAlias("fmt", "; Compile / scalafmt; Test / scalafmt; scalafmtSbt")
addCommandAlias("fmtCheck", "; Compile / scalafmtCheck; Test / scalafmtCheck; scalafmtSbtCheck")
