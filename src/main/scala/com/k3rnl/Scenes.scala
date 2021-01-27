package com.k3rnl

import com.k3rnl.Animators.{MoveCamera, MoveLight, xCos, ySin, zCos, zSin}
import com.k3rnl.Constants.{Color, Epsilon}
import com.k3rnl.Materials.{Glass, Metal}
import com.k3rnl.Shaders.{lambertShading, reflectionShading, refractionShading, specularLightShading}
import com.k3rnl.Vectors.Vector3d


object Scenes {

  val test1: Scene = Scene(512, 512, new Vector3d(0, 10, 70),
    light = Vector3(0, 20, 40),
    objects = List(
      Sphere(Vector3(0, 15, 20), R = 5, color = new Color(0.95, 1, 0.2), material = Metal), Sphere(Vector3(-20, 5, 0), R = 5), Sphere(Vector3(10, 25, 10), R = 5),
      Plan(Vector3(0, 0, 0), material = Metal)
    ),
    shaders = List(lambertShading, specularLightShading, reflectionShading),
    animators = List(MoveLight(20, List(xCos, zSin)))
  )

  val test2: Scene = Scene(512, 512, new Vector3d(0, 10, 70),
    light = Vector3(0, 20, 50),
    objects = List(
      Sphere(Vector3(0, 0, 0), R = 5, color = new Color(0.95, 1, 0.2)),
      Plan(Vector3(0, 0, 0))
    ),
    shaders = List(lambertShading, specularLightShading)
  )

  val refraction1: Scene = Scene(512, 512, new Vector3d(0, 0, 70),
//    background = new Color(0.52, 0.80, 0.97),
    light = Vector3(0, 0, 60),
    objects = List(
//      Sphere(Vector3(0, 0, 0), R = 550, color = new Color(1, 0, 0)),
      Sphere(Vector3(0, 0, 40), R = 5, material = Glass),
      Sphere(Vector3(-10, 10, 0), R = 5, color = new Color(1, 0, 0)), Sphere(Vector3(10, 10, 0), R = 5, color = new Color(0, 1, 0)),
      Sphere(Vector3(-10, -10, 0), R = 5, color = new Color(0, 0, 1)), Sphere(Vector3(10, -10, 0), R = 5),
      //      Plan(Vector3(0, -20, 0), material = Metal)
    ),
    shaders = List(lambertShading, specularLightShading, reflectionShading, refractionShading),
    animators = List(MoveCamera(23, List(xCos, ySin)), MoveLight(23, List(xCos, ySin)))
  )
}
