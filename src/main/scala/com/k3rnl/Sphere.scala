package com.k3rnl

import com.k3rnl.Ray.Intersection

import math.sqrt
import com.k3rnl.Vectors.Vector3d

class Sphere(position: Vector3d, var R: Float) extends Object(position) {
  override def intersect(ray: Ray): Intersection = {
    val oc = ray.position - position
    val a = ray.direction dot ray.direction
    val b = 2.0f * (oc dot ray.direction)
    val c = (oc dot oc) - R * R
    val d = b * b - 4 * a * c

    if (d < Constants.Epsilon)
      return ray.missed()
    ray.intersection(on = this, withDistance = (-b - sqrt(d)) / (2.0 * a))
  }

  override def normal(intersection: Intersection): Vector3d = (position - intersection.position).normalized
}
