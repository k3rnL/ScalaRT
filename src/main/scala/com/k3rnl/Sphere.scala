package com.k3rnl

import com.k3rnl.Animators.ObjectAnimator
import com.k3rnl.Constants.{Color, Colors}
import com.k3rnl.Materials.Material
import com.k3rnl.Ray.Intersection

import math.sqrt
import com.k3rnl.Vectors.Vector3d

case class Sphere(override val position: Vector3d,
                  override val material: Material = Materials.Mat,
                  override val color: Color = Colors.White,
                  override val animators: List[ObjectAnimator] = List(),
                  R: Float)
  extends Object(position, material, color) {

  override def intersect(ray: Ray): Intersection = {
    val oc = ray.position - position
    val a = ray.direction dot ray.direction
    val b = 2.0f * (oc dot ray.direction)
    val c = (oc dot oc) - R * R
    val d = b * b - 4 * a * c

    if (d < Constants.Epsilon)
      return ray.missed()
    val distance = (-b - sqrt(d)) / (2.0 * a)

    if (distance < Constants.Epsilon) ray.missed() else ray.intersection(on = this, withDistance = distance)
  }

  override def normal(intersection: Intersection): Vector3d = (intersection.position - position).normalized

}
