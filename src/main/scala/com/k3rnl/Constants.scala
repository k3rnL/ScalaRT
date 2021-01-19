package com.k3rnl

import java.awt.Color

import com.k3rnl.Vectors.Vector3i

object Constants {
  val Epsilon = 0.01
  val fov = 60

  object Colors {
    val White = new Color(0xffffff)
    val Ambient = new Color(0x0A0A0A)
    val Black = new Color(0x0)

    def getRed(value: Int): Byte = {
      ((value & 0x00FF0000) >> 16).asInstanceOf[Byte]
    }

    def getGreen(value: Int): Byte = {
      ((value & 0x0000FF00) >> 8).asInstanceOf[Byte]
    }

    def getBlue(value: Int): Byte = {
      (value & 0x000000FF).asInstanceOf[Byte]
    }

    def toInt(color: Color): Int = (color.x << 16) + (color.y << 8) + color.z
    def toInt(color: Vector3i): Int = (color.x << 16) + (color.y << 8) + color.z
  }

  class Color(value: Int) extends Vector3[Int](Colors.getRed(value), Colors.getGreen(value), Colors.getBlue(value))

}
