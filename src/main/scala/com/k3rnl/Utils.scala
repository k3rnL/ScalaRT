package com.k3rnl

import spire.math._
import spire.implicits._

object Utils {

  def clamp[T: Numeric](minimum: T, maximum: T, value: T): T = max(min(value, maximum), minimum)

}
