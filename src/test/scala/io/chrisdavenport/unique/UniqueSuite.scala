package io.chrisdavenport.unique

import cats._
import cats.tests.CatsSuite
// import cats.implicits._
import cats.effect._

class UniqueSuite extends CatsSuite {

  test("Equality - be equal when comparing the same value"){
    val test = for {
      unique <- Unique.newUnique[IO]
    } yield Eq[Unique].eqv(unique, unique)
    test.unsafeRunSync should be (true)
  }

  test("Non-Equality - Not be equal when comparing different values"){
    val test = for {
      unique1 <- Unique.newUnique[IO]
      unique2 <- Unique.newUnique[IO]
    } yield Eq[Unique].eqv(unique1,unique2)

    test.unsafeRunSync should be (false)
  }
}
