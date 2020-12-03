---
layout: home

---
# unique [![Build Status](https://travis-ci.com/ChristopherDavenport/unique.svg?branch=master)](https://travis-ci.com/ChristopherDavenport/unique) [![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.chrisdavenport/unique_2.12/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.chrisdavenport/unique_2.12)

This is a shared library for creating and managing unique values in a referentially transparent way.

Creation of a unique value must always happen within an effect.

Let's start with some imports.

```tut:silent
import io.chrisdavenport.unique.Unique
import cats.implicits._
import cats.effect._
```

Then we can display that only values created by the same effect are equal.

```tut:book
import cats.effect.unsafe.implicits.global

val equal = for {
  unique <- Unique.newUnique[IO]
} yield unique === unique

equal.unsafeRunSync // For Display

val notEqual = for {
  unique1 <- Unique.newUnique[IO]
  unique2 <- Unique.newUnique[IO]
} yield unique1 === unique2

notEqual.unsafeRunSync // For Display
```
