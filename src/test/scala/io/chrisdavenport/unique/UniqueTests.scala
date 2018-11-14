package io.chrisdavenport.unique

import org.scalacheck._
import cats.effect.IO
import cats.tests.CatsSuite
import cats.kernel.laws.discipline.{EqTests, HashTests}


class UniqueTests extends CatsSuite {

  implicit def functionArbitrary[B, A: Arbitrary]: Arbitrary[B => A] = Arbitrary{
    for {
      a <- Arbitrary.arbitrary[A]
    } yield {_: B => a}
  }

  implicit val uniqueArb : Arbitrary[Unique] = Arbitrary{
    Arbitrary.arbitrary[Unit].map(_ => Unique.newUnique[IO].unsafeRunSync)
  }


  checkAll("Unique", HashTests[Unique].hash)
  checkAll("Unique", EqTests[Unique].eqv)
}
