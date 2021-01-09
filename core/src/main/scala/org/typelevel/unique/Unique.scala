package org.typelevel.unique

import cats.Hash
import cats.effect.Sync

final class Unique private extends Serializable {
  override def toString: String = s"Unique(${hashCode.toHexString})"
}
object Unique {
  def newUnique[F[_]: Sync]: F[Unique] = Sync[F].delay(new Unique)

  implicit val uniqueInstances : Hash[Unique] =
    Hash.fromUniversalHashCode[Unique]
}
