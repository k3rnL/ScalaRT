package com.k3rnl

import com.k3rnl.Ray.Intersection
import com.k3rnl.Vectors.Vector3d

class Ray(var position: Vector3d, var direction: Vector3d) {

  def intersection(on: Object, withDistance: Double): Intersection = new Intersection(this,true, on, withDistance)
  def missed(): Intersection = Ray.nullIntersection

}

object Ray {
  class Intersection(val ray: Ray, val hit: Boolean, val that: Object, val distance: Double) {
    val position: Vector3d = ray.position + ray.direction * distance

    def normal: Vector3d = that.normal(this)
    def reflectedRay: Ray = new Ray(position, ray.direction - normal * 2 * (ray.direction dot normal))

    override def toString: String = s"hit: $hit, at $distance on " + position
  }

  def min(a: Intersection, b: Intersection): Intersection = if (a.distance < b.distance) a else b

  val nullRay = new Ray(Vector3(0, 0, 0), Vector3(0, 0, 0))
  val nullIntersection = new Intersection(nullRay, false, null, Double.MaxValue)
}