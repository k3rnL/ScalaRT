package com.k3rnl

import com.k3rnl.Animators.{MoveCamera, MoveLight, xCos, ySin, zCos, zSin}
import com.k3rnl.Constants.{Color}
import com.k3rnl.ImageOutputs.{ShellImageOutput, WindowOutput}
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

  val test2: Scene = Scene(150, 40, new Vector3d(0, 5, 20),
    light = Vector3(0, 1, 0),
    objects = List(
      Sphere(Vector3(0, 0, 0), R = 5, color = new Color(0.95, 1, 0.2)),
      Plan(Vector3(0, 0, 0))
    ),
    shaders = List(lambertShading, specularLightShading),
    imageOutput = new ShellImageOutput,
    animators = List(MoveLight(5, List(xCos, zSin)))
  )

  val test3: Scene = Scene(510, 200, new Vector3d(0, 5, 20),
    light = Vector3(0, 15, 10),
    objects = List(
      Sphere(Vector3(0, 0, 0), R = 5, color = new Color(0.95, 1, 0.2)),
      Plan(Vector3(0, 0, 0))
    ),
    shaders = List(lambertShading, specularLightShading),
    imageOutput = new WindowOutput,
    animators = List(MoveLight(10, List(xCos, zSin)))
  )

  val refraction1: Scene = Scene(512, 512, new Vector3d(0, 0, 70),
    light = Vector3(0, 0, 110),
    objects = List(
      Sphere(Vector3(0, 0, 40), R = 6, material = Glass),
      Sphere(Vector3(-10, 10, 0), R = 8, color = new Color(1, 0, 0)), Sphere(Vector3(10, 10, 0), R = 8, color = new Color(0, 1, 0)),
      Sphere(Vector3(-10, -10, 0), R = 8, color = new Color(0, 0, 1)), Sphere(Vector3(10, -10, 0), R = 8),
    ),
    shaders = List(lambertShading, specularLightShading, reflectionShading, refractionShading),
    animators = List(MoveCamera(2, List(xCos, ySin)), MoveLight(45, List(xCos, ySin)))
  )
}
