# changelog

This file summarizes **notable** changes for each release, but does not describe internal changes unless they are particularly exciting. This change log is ordered chronologically, so each release contains all changes described below it.

----

## <a name="2.1.0"></a>New and Noteworthy for Version 2.1.0

- cats-2.4.2
- cats-effect-2.3.3
- Dropped Scala-3.0.0-M2
- Added Scala 3.0.0-RC1

## <a name="2.1.0-M7"></a>New and Noteworthy for Version 2.1.0-M7

- Published under `org.typelevel`
- Base package moved to `org.typelevel.unique`
- cats-2.3.1
- cats-effect-2.3.1
- Cross-built for Scala 3.0.0-M3, 3.0.0-M2, 2.13, and 2.12

## <a name="2.0.0"></a>New and Noteworthy for Version 2.0.0

- cats 2.0.0
- cats-effect 2.0.0

## <a name="2.0.0-RC1"></a>New and Noteworthy for Version 2.0.0-RC1

- Update Off Scala 2.13
- Cats 2.0-RC1
- Cats-Effect 2.0-RC1
- Got off Catsuite
- Drop 2.11 Support

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
