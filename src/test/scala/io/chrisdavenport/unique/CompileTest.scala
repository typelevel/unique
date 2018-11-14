package io.chrisdavenport.unique

import cats.implicits._

object CompileTest {

  def compare(a: Unique, b: Unique): Boolean = a === b
}