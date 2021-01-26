package com.k3rnl

import com.k3rnl.Animators.{Animated, ObjectAnimator}
import com.k3rnl.Constants.{Color, Colors}
import com.k3rnl.Ray.Intersection
import com.k3rnl.Vectors.Vector3d

abstract class Object(val position: Vector3d,
                      val material: Material = Materials.Mat,
                      val color: Color = Colors.White,
                      val animators: List[ObjectAnimator] = List())
  extends Animated[Object] {

  def intersect(ray: Ray): Intersection
  def normal(intersection: Intersection): Vector3d
}
