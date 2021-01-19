package com.k3rnl

import spire.algebra._
import spire.implicits._
import spire.math._
import math.pow

case class Vector3[T: Numeric](x: T, y: T, z: T) {
  def this(scalar: T) = this(scalar, scalar, scalar)

  def *(that: Vector3[T]): Vector3[T] = Vector3[T](x * that.x, y * that.y, z * that.z)
  def +(that: Vector3[T]): Vector3[T] = Vector3[T](x + that.x, y + that.y, z + that.z)
  def -(that: Vector3[T]): Vector3[T] = Vector3[T](x - that.x, y - that.y, z - that.z)

  def *(that: T): Vector3[T] = Vector3[T](x * that, y * that, z * that)
  def +(that: T): Vector3[T] = Vector3[T](x + that, y + that, z + that)
  def -(that: T): Vector3[T] = Vector3[T](x - that, y - that, z - that)

  def dot(that: Vector3[T]): T = x * that.x + y * that.y + z * that.z
  def length(): T = dot(this).sqrt()
  def normalized: Vector3[T] = Vector3[T](x / length(), y / length(), z / length())

  def map[B: Numeric](f: T => B): Vector3[B] = Vector3(f(x), f(y), f(z))
}

object Vectors {

  implicit class Ops[T](x: T)(implicit ev: Numeric[T]) {
    def *(that: Vector3[T]): Vector3[T] = that * x
  }

  implicit def convert[A: Numeric, B: Numeric](that: Vector[A])(implicit f: A => B): Vector[B] = that.map(f(_))

  type Vector3f = Vector3[Float]
  type Vector3d = Vector3[Double]
  type Vector3i = Vector3[Int]
}