---
layout: home

---
# unique [![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.typelevel/unique_2.12/badge.svg)](https://maven-badges.herokuapp.com/maven-central/org.typelevel/unique_2.12)

This is a shared library for creating and managing unique values in a referentially transparent way.

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
