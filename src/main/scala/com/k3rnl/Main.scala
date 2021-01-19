package com.k3rnl

import java.awt.{Graphics, Graphics2D}

import scala.swing._
import spire.implicits._
import spire.math._
import spire.algebra._
import java.awt.image.BufferedImage
import java.io.{File, OutputStream}

import com.k3rnl.Constants.{Color, Colors}
import com.k3rnl.Ray.Intersection
import com.k3rnl.Vectors.{Vector3d, Vector3f, Vector3i}
import javax.imageio.{ImageIO, ImageTypeSpecifier}
import javax.swing.{ImageIcon, JComponent}

object Main extends App {

  override def main(args: Array[String]): Unit = {

    var test = Vector3(10.0f, 10.0f, 10.0f) + Vector3(10, 10, 10)
    var light: Vector3d = Vector3(0, 0, 10)
    //light = 5.0 * light
    var count = 0
    val scene = Scenes.test1
    val image = new WindowOutput(scene.sizeX, scene.sizeY)
    while (true) {

//      val image = new BMPImageOutput(count.toString + ".bmp", scene.sizeX, scene.sizeY)
      //      val image = new ShellImageOutput(SIZE_X, SIZE_Y)

      //      Thread.sleep(500)
      count = count + 1
      val pos: Double = cos(count.toDouble / 2) * 10.0

      for (y <- 0 until scene.sizeY; x <- 0 until scene.sizeX) {
        val imageAspectRatio = scene.sizeX / scene.sizeY.toFloat; // assuming width > height
        val Px = (2 * ((x + 0.5) / scene.sizeX) - 1) * tan(Constants.fov / 2 * math.Pi / 180) * imageAspectRatio
        val Py = (1 - 2 * ((y + 0.5) / scene.sizeY)) * tan(Constants.fov / 2 * math.Pi / 180)

        val ray = new Ray(scene.position + Vector3(pos, 0, 0), Vector3(Px, Py, -1).normalized)

        val intersection = scene.objects.map(o => o intersect ray).foldLeft(Ray.nullIntersection)(Ray.min)

        if (intersection.hit) {
          val N = intersection.normal
          val L = (intersection.position - light).normalized
          val angle = max(min(N dot L, 1), 0.1)
          val shaded = new Vector3i((0xff * angle) toInt, (0xff * angle) toInt, (0xff * angle) toInt)

          val color: Vector3i = if (intersection.hit) shaded else Colors.Black
          image.write(Colors.toInt(color))
        }
        else
          image.write(Colors toInt Colors.Black)

      }

//      println("Image ready, writing..")
      image.close()
//      println("OK.")
    }
  }
}

trait ImageOutput extends OutputStream

object ShaderLambert {
  def shade(light: Vector3d, intersection: Intersection): Color = {
    return Colors.Ambient
  }
}

class ShellImageOutput(sizeX: Int, sizeY: Int) extends ImageOutput {
  var x: Int = 0
  var y: Int = 0

  override def write(color: Int): Unit = {
    x += 1

    //    print('X')
    if (color > 0) print('X') else print(' ')

    if (x == sizeX) {
      println()
      x = 0
      y += 1
      if (y == sizeY) {
        println("-------")
        y = 0
      }
    }
  }
}

class BMPImageOutput(toFile: String, sizeX: Int, sizeY: Int) extends ImageOutput {
  var x = 0
  var y = 0
  var image = new BufferedImage(sizeX, sizeY, BufferedImage.TYPE_INT_RGB)

  override def write(color: Int): Unit = {
    image.setRGB(x, sizeY - y - 1, color)

    if (x + 1 == sizeX)
      y = (y + 1) % sizeY
    x = (x + 1) % sizeX
  }

  override def close(): Unit = {
    ImageIO.write(image, "BMP", new File(toFile))
  }
}

class WindowOutput(val sizeX: Int, val sizeY: Int) extends ImageOutput {
  var x = 0
  var y = 0
  var image = new BufferedImage(sizeX, sizeY, BufferedImage.TYPE_INT_RGB)
  val window: MainFrame = new MainFrame() {
    title = "Ray Tracer"
    preferredSize = new Dimension(sizeX, sizeY)
    contents = new ImageComponent(image, sizeX, sizeY)
  }

  override def write(color: Int): Unit = {
    image.setRGB(x, sizeY - y - 1, color)

    if (x + 1 == sizeX)
      y = (y + 1) % sizeY
    x = (x + 1) % sizeX
  }

  override def close(): Unit = {
    window.contents.head.asInstanceOf[ImageComponent].setImage(image)
    image = new BufferedImage(sizeX, sizeY, BufferedImage.TYPE_INT_RGB)
    if (!window.visible)
      window.visible = true
  }
}

class ImageComponent(var img: BufferedImage, sizeX: Int, sizeY: Int) extends Component {

  def setImage(img: BufferedImage): Unit = {
    this.img = img
    this.repaint()
  }

  override protected def paintComponent(g: Graphics2D): Unit = {
    super.paintComponent(g.asInstanceOf[Graphics2D])
    val _ = g.drawImage(img, 0, 0, sizeX, sizeY, null)
  }

}