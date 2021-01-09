package org.typelevel.unique

import cats._
import cats.effect._
import munit.CatsEffectSuite

class UniqueSuite extends CatsEffectSuite {

  test("Equality - be equal when comparing the same value"){
    for {
      unique <- Unique.newUnique[IO]
    } yield assert(Eq[Unique].eqv(unique, unique))
  }

  test("Non-Equality - Not be equal when comparing different values"){
    for {
      unique1 <- Unique.newUnique[IO]
      unique2 <- Unique.newUnique[IO]
    } yield assert(Eq[Unique].neqv(unique1,unique2))
  }
}
