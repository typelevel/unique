# changelog

This file summarizes **notable** changes for each release, but does not describe internal changes unless they are particularly exciting. This change log is ordered chronologically, so each release contains all changes described below it.

----

## <a name="Unreleased"></a>Unreleased Changes

## <a name="1.0.0"></a>New and Noteworthy for Version 1.0.0

Stable Release of `unique`. This library will maintain binary compatibility moving forward for the forseeable future.

- [#18](https://github.com/ChristopherDavenport/unique/pull/18) Scala 2.13 Integration

Upgrades:

- cats 1.6.0
- cats-effect 1.2.0

## <a name="0.1.1"></a>New and Noteworthy for Version 0.1.1

Incorporate Useability Improvements

- [#3](https://github.com/ChristopherDavenport/unique/pull/3) Better `toString` for readability, and extends `Serializable`.
- [#2](https://github.com/ChristopherDavenport/unique/pull/2) Add MiMa for binary compatiblity checking.

## <a name="0.1.0"></a>New and Noteworthy for Version 0.1.0

Initial Release of `unique`. Create unique values that are equal only to the same effectfully created value. Scala.js project released as well. Fully law checked. [cats-effect](https://github.com/typelevel/cats-effect) is the only dependency and this version is based off 1.0.0.