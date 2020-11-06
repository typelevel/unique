package io.chrisdavenport.unique

import cats._
import cats.implicits._
// import org.specs2.mutable.Specification
// import cats.implicits._
import cats.effect._
import cats.effect.unsafe.implicits.global

class UniqueSuite extends munit.FunSuite {

  test("Equality - be equal when comparing the same value") {
    val test = for {
      unique <- Unique.newUnique[IO]
    } yield Eq[Unique].eqv(unique, unique)
    assertEquals(test.unsafeRunSync(), true)
  }

  test("Non-Equality - Not be equal when comparing different values") {
    val test = for {
      unique1 <- Unique.newUnique[IO]
      unique2 <- Unique.newUnique[IO]
    } yield Eq[Unique].eqv(unique1,unique2)

    assertEquals(test.unsafeRunSync(), false)
  }
}
