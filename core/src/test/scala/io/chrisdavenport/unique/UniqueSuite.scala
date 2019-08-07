package io.chrisdavenport.unique

import cats._
import org.specs2.mutable.Specification
// import cats.implicits._
import cats.effect._

class UniqueSuite extends Specification {

  "Equality - be equal when comparing the same value" should {
    val test = for {
      unique <- Unique.newUnique[IO]
    } yield Eq[Unique].eqv(unique, unique)
    test.unsafeRunSync should_=== (true)
  }

  "Non-Equality - Not be equal when comparing different values" should {
    val test = for {
      unique1 <- Unique.newUnique[IO]
      unique2 <- Unique.newUnique[IO]
    } yield Eq[Unique].eqv(unique1,unique2)

    test.unsafeRunSync should_=== (false)
  }
}
