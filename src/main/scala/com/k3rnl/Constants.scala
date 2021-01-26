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
//    def toInt(color: Vector3i): Int = (color.x << 16) + (color.y << 8) + color.z
  }

//  class Color(r: Int, g: Int, b: Int) extends Vector3[Int](r, g, b) {
//    def this(value: Int) = this(Colors.getRed(value), Colors.getGreen(value), Colors.getBlue(value))
//  }

  type Color = Vector3d

  implicit class ColorToInt(color: Color) {
    def toInt: Int = Colors.toInt(color)
  }

//  implicit def vector3ToColor[T: Numeric](vec: Vector3[T])(implicit ev: Numeric[T]): Color = new Color(ev.toInt(vec.x), ev.toInt(vec.y), ev.toInt(vec.z))
//  implicit def colorToVector3[T: Numeric](col: Color)(implicit ev: Numeric[T]): Vector3[T] = Vector3(ev.fromInt(col.x), ev.fromInt(col.y), ev.fromInt(col.z))(ev)

  class Material()
}
