package com.k3rnl

import scala.math.cos

object Test {
  def main(args: Array[String]): Unit = {
    while (true) {
      println(cos(System.nanoTime()/10000000000.0) + " " + System.nanoTime())
    }
  }
}
