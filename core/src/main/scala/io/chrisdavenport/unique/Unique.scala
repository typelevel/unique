package io.chrisdavenport.unique

import cats.Hash
import cats.effect.Sync

final class Unique private {}
object Unique {
  def newUnique[F[_]: Sync]: F[Unique] = Sync[F].delay(new Unique)

  implicit val uniqueInstances : Hash[Unique] =
    Hash.fromUniversalHashCode[Unique]
}