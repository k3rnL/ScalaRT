package com.k3rnl

import com.k3rnl.Ray.Intersection
import com.k3rnl.Vectors.Vector3d

abstract class Object(var position: Vector3d) {

  def intersect(ray: Ray): Intersection

  def normal(intersection: Intersection): Vector3d
}
