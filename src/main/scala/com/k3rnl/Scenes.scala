package com.k3rnl

import com.k3rnl.Vectors.Vector3d

object Scenes {

  val test1 = new Scene(1280, 720, new Vector3d(0, 0, 50),
    List(
      new Sphere(Vector3(0, 0 ,0), 5), new Sphere(Vector3(-20, 0 ,0), 5), new Sphere(Vector3(10, 10 ,10), 5),
      new Plan(Vector3(0, -10, 0)))
  )

}
