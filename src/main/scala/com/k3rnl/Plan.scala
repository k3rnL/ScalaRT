package com.k3rnl

import com.k3rnl.Ray.Intersection
import com.k3rnl.Vectors.Vector3d

import scala.math.sqrt

class Plan(position: Vector3d) extends Object(position) {
  override def intersect(ray: Ray): Intersection = {
    val d = -position.y / ray.direction.y

    if (d < Constants.Epsilon)
      return ray.missed()
    ray.intersection(on = this, withDistance = d)
  }

  override def normal(intersection: Intersection): Vector3d = Vector3(0, 1, 0)
}
