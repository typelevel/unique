# unique [![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.typelevel/unique_2.12/badge.svg)](https://maven-badges.herokuapp.com/maven-central/org.typelevel/unique_2.12) ![Continuous Integration](https://github.com/typelevel/unique/workflows/Continuous%20Integration/badge.svg)

This is a shared library for creating and managing unique values in a referentially transparent way.

## End-of-life

This project is now end-of-life.  Its functionality has been [adopted into Cats-Effect 3](https://typelevel.github.io/unique).  We will consider releases for [security](https://github.com/typelevel/unique/security/policy) or other significant tales of woe, but routine maintenance has ceased.

## [Head on over to the microsite](https://typelevel.github.io/unique)

## Quick Start

To use Unique in an existing SBT project with Scala 2.12 or a later version, add the following dependencies to your
`build.sbt` depending on your needs:

```scala
libraryDependencies ++= Seq(
  "org.typelevel" %% "unique"     % "<version>"
)
```
