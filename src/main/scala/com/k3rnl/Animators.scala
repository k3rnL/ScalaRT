package com.k3rnl

import com.k3rnl.Vectors.{Vector3d, Vector3f}
import spire.implicits._
import spire.math._

object Animators {

  trait Animated[T] {
    val animators: List[Animator[T]]
  }

  trait Positionable[T] {
    val position: Vector3d
  }

  type Animator[T] = (Double, T) => T

  trait Animation

  implicit class AnimatedAll[T <: Animated[T]](animated: T) {
    def animate(seed: Double): T = animated.animators.foldLeft(animated)((value, animator) => animator(seed, value))
  }

  abstract class ObjectAnimator extends Animator[Object]

  type VectorAnimation = (Double, Double, Vector3d) => Vector3d
  val xCos: VectorAnimation = (seed, multiplier, v) => Vector3(v.x + cos(seed) * multiplier, v.y, v.z)
  val yCos: VectorAnimation = (seed, multiplier, v) => Vector3(v.x, v.y + cos(seed) * multiplier, v.z)
  val zCos: VectorAnimation = (seed, multiplier, v) => Vector3(v.x, v.y, v.z + cos(seed) * multiplier)

  val xSin: VectorAnimation = (seed, multiplier, v) => Vector3(v.x + sin(seed) * multiplier, v.y, v.z)
  val ySin: VectorAnimation = (seed, multiplier, v) => Vector3(v.x, v.y + sin(seed) * multiplier, v.z)
  val zSin: VectorAnimation = (seed, multiplier, v) => Vector3(v.x, v.y, v.z + sin(seed) * multiplier)

  case class MoveCamera(multiplier: Double, animations: List[VectorAnimation]) extends Animator[Scene] {
    def apply(seed: Double, scene: Scene): Scene =
      scene.copy(position = animations.foldLeft(scene.position)((pos, animation) => animation(seed, multiplier, pos)))
  }

  case class MoveLight(multiplier: Double, animations: List[VectorAnimation]) extends Animator[Scene] {
    def apply(seed: Double, scene: Scene): Scene =
      scene.copy(light = animations.foldLeft(scene.light)((pos, animation) => animation(seed, multiplier, pos)))
  }

}
