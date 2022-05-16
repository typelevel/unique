---
layout: home

---
# unique [![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.typelevel/unique_2.12/badge.svg)](https://maven-badges.herokuapp.com/maven-central/org.typelevel/unique_2.12)

This is a shared library for creating and managing unique values in a referentially transparent way.

## Maintenance status

This project is now end-of-life.  Its functionality has been [adopted into Cats-Effect 3](https://typelevel.github.io/unique).  We will consider releases for [security](https://github.com/typelevel/unique/security/policy) or other significant tales of woe, but routine maintenance has ceased.

## Synopsis

Creation of a unique value must always happen within an effect.

Let's start with some imports.

```scala mdoc:silent
import org.typelevel.unique.Unique
import cats.implicits._
import cats.effect._
```

Then we can display that only values created by the same effect are equal.

```scala mdoc
// Equal
{
  for {
    unique <- Unique.newUnique[IO]
  } yield unique === unique
}.unsafeRunSync()

// Not equal
{
  for {
    unique1 <- Unique.newUnique[IO]
    unique2 <- Unique.newUnique[IO]
  } yield unique1 === unique2
}.unsafeRunSync()


```
