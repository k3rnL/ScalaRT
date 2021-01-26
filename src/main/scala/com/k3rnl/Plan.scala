package com.k3rnl

import com.k3rnl.Constants.{Color, Colors}
import com.k3rnl.Ray.Intersection
import com.k3rnl.Vectors.Vector3d


case class Plan(override val position: Vector3d,
                override val material: Material = Materials.Mat,
                override val color: Color = Colors.White)
  extends Object(position, material, color) {

  override def intersect(ray: Ray): Intersection = {
    val d = -(ray.position.y - position.y) / ray.direction.y

    if (d < Constants.Epsilon)
      return ray.missed()
    ray.intersection(on = this, withDistance = d)
  }

  override def normal(intersection: Intersection): Vector3d = Vector3(0, 1, 0)
}
