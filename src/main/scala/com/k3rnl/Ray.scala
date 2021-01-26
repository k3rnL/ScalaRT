package com.k3rnl

import com.k3rnl.Ray.Intersection
import com.k3rnl.Utils.clamp
import com.k3rnl.Vectors.Vector3d

import spire.math._
import spire.implicits._

class Ray(var position: Vector3d, var direction: Vector3d) {

  def intersect(objects: List[Object]): Intersection = objects.map(o => o intersect this).foldLeft(Ray.nullIntersection)(Ray.min)
  def intersect(scene: Scene): Intersection = this intersect scene.objects
  def intersect(obj: Object): Intersection = obj intersect this

  def intersection(on: Object, withDistance: Double): Intersection = new Intersection(this,true, on, withDistance)
  def missed(): Intersection = Ray.nullIntersection

}

object Ray {
  class Intersection(val ray: Ray, val hit: Boolean, val that: Object, val distance: Double) {
    val position: Vector3d = ray.position + ray.direction * distance

    def normal: Vector3d = that.normal(this)

    def reflectedVector(vector: Vector3d): Ray = new Ray(position, vector - normal * 2 * (vector dot normal))
    def reflectedRay(ray: Ray): Ray = new Ray(position, ray.direction - normal * 2 * (ray.direction dot normal))
    def reflectedRay: Ray = reflectedRay(ray)

    def refractedRay: Ray = {
      var cosi = clamp(-1, 1, ray.direction dot normal)
      var etai = 1.0
      var etat = that.material.refraction;
      var N = normal
      if (cosi < 0) { cosi = -cosi; } else {
        val tmp = etai
        etai = etat
        etat = tmp
        N = N.negate;
      }
      val eta = etai / etat;
      val k = 1 - eta * eta * (1 - cosi * cosi);
      val dir = if (k < 0) new Vector3d else ray.direction * eta + N * (eta * cosi - sqrt(k))
      new Ray(position, dir)
    }


    def shadowRay(light: Vector3d): Ray = new Ray(light, (position - light).normalized)

    override def toString: String = s"hit: $hit, at $distance on " + position
  }

  def min(a: Intersection, b: Intersection): Intersection = if (a.distance < b.distance) a else b

  val nullRay = new Ray(Vector3(0, 0, 0), Vector3(0, 0, 0))
  val nullIntersection = new Intersection(nullRay, false, null, Double.MaxValue)
}