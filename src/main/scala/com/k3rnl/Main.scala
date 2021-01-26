package com.k3rnl

import java.awt.{Graphics, Graphics2D}
import scala.swing._
import spire.implicits._
import spire.math._
import spire.algebra._

import com.k3rnl.Constants.{Color, Colors}
import com.k3rnl.Constants._
import com.k3rnl.Constants.Colors._
import com.k3rnl.ImageOutputs.WindowOutput
import com.k3rnl.Shaders.ShaderList
import com.k3rnl.Vectors.{Vector3d, Vector3f, Vector3i}


object Main extends App {

  override def main(args: Array[String]): Unit = {

    val _scene = Scenes.test2
    val image = new WindowOutput(_scene.sizeX, _scene.sizeY)
    while (true) {
      val scene = _scene.animate(System.nanoTime() / 1000000000.0)

      //      val image = new BMPImageOutput(count.toString + ".bmp", scene.sizeX, scene.sizeY)
      //      val image = new ShellImageOutput(SIZE_X, SIZE_Y)

      for (y <- (0 until scene.sizeY).par; x <- (0 until scene.sizeX)) yield {
        val imageAspectRatio = scene.sizeX / scene.sizeY.toFloat; // assuming width > height
        val Px = (2 * ((x + 0.5) / scene.sizeX) - 1) * tan(Constants.fov / 2 * math.Pi / 180) * imageAspectRatio
        val Py = (1 - 2 * ((y + 0.5) / scene.sizeY)) * tan(Constants.fov / 2 * math.Pi / 180)

        val ray = new Ray(scene.position, Vector3(Px, Py, -1).normalized)

        val intersection = ray intersect scene

        if (intersection.hit) {
          val color: Color = scene.shaders.shade(intersection, scene)
          image.write(color toInt, x, y)
        }
        else
          image.write(scene.background toInt, x, y)

      }

      image.close()
    }
  }
}
