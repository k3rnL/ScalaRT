package com.k3rnl

import java.awt.Color
import spire.math._
import com.k3rnl.Vectors.{Vector3d, Vector3i}

object Constants {
  val Epsilon = 0.0000001
  val fov = 60

  object Colors {
    val White = new Color(1)
    val Ambient = new Color(0.1)
    val Black = new Color(0)

    def getRed(value: Int): Byte = {
      ((value & 0x00FF0000) >> 16).asInstanceOf[Byte]
    }

    def getGreen(value: Int): Byte = {
      ((value & 0x0000FF00) >> 8).asInstanceOf[Byte]
    }

    def getBlue(value: Int): Byte = {
      (value & 0x000000FF).asInstanceOf[Byte]
    }

    def toInt(color: Color): Int = (((color.x * 255) toInt) << 16) + (((color.y * 255) toInt) << 8) + ((color.z * 255) toInt)
  }

  type Color = Vector3d

  implicit class ColorToInt(color: Color) {
    def toInt: Int = Colors.toInt(color)
  }

}
