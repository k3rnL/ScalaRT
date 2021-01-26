package com.k3rnl

import com.k3rnl.Constants.{Color, Colors}
import com.k3rnl.Ray.Intersection
import com.k3rnl.Utils.clamp
import spire.math.{max, pow}


object Shaders {

  type Shader = (Intersection, Scene) => Color

  implicit class ShaderList(shaders: List[Shader]) {
    def shade: Shader = (intersection, scene) => shaders.map(_.apply(intersection, scene)).reduce(_ + _)
  }

  val lambertShading: Shader = (intersection, scene) => {
    val light = scene.light
    val material = intersection.that.material
    val N = intersection.normal
    val L = (light - intersection.position).normalized
    val diffusion = clamp(0, 1, N dot L)

    var shadow = false
    for (elem <- scene.objects if elem != intersection.that && !shadow) {
      val inter = elem intersect intersection.shadowRay(light)
      if (inter.hit && inter.distance < (light - intersection.position).length)
        shadow = true
    }

    if (shadow) scene.background * material.ambient else intersection.that.color * diffusion * material.diffuse
  }

  val specularLightShading: Shader = (intersection, scene) => {
    val light = scene.light
    val material = intersection.that.material
    if (material.specular > 0) {
      val L = (light - intersection.position).normalized
      val R = intersection.reflectedVector(L).direction
      val specular = pow(max(0, R.dot(intersection.ray.direction)), material.roughness)

      var shadow = false
      for (elem <- scene.objects if elem != intersection.that && !shadow) {
        val inter = elem intersect intersection.shadowRay(light)
        if (inter.hit && inter.distance < (light - intersection.position).length)
          shadow = true
      }

      if (shadow) Colors.Black else Colors.White * specular * material.specular
    }
    else Colors.Black
  }

  val reflectionShading: Shader = (intersection, scene) => {
    val material = intersection.that.material
    if (material.reflection > 0) {
      var color: Color = Colors.Black
      var bounce = intersection
      for (_ <- 0 until 10 if bounce.hit && bounce.that.material.reflection != 0) {
        val inter = bounce.reflectedRay intersect scene
        if (inter.hit) {
          val shadedColor = scene.shaders.filter(_ != reflectionShading).map(_ (inter, scene)).reduce(_ + _)
          color = color + shadedColor * bounce.that.material.reflection
        }

        bounce = inter
      }

      color
    }
    else Colors.Black
  }

  val refractionShading: Shader = (intersection, scene) => {
    val material = intersection.that.material
    if (material.refraction > 0) {
      var color: Color = Colors.Black
      var bounce = intersection
      for (_ <- 0 until 10 if bounce.hit && bounce.that.material.refraction != 0) {
        val inter = bounce.refractedRay intersect scene
        if (inter.hit) {
          val shadedColor = scene.shaders.filter(_ != reflectionShading).map(_ (inter, scene)).reduce(_ + _)
          color = color + shadedColor
        }
        else color += scene.background

        bounce = inter
      }

      color
    }
    else Colors.Black
  }

}
