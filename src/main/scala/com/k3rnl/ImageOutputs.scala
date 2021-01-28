package com.k3rnl

import com.k3rnl.Constants.{Color, Colors}

import java.awt.Graphics2D
import java.awt.image.BufferedImage
import java.io.File
import java.nio.file
import javax.imageio.ImageIO
import scala.reflect.io.{Directory, Path}
import scala.swing.{Component, Dimension, MainFrame}

trait Image extends AutoCloseable {
  def write(color: Color, x: Int, y: Int)
}

trait ImageOutput {
  def image(sizeX: Int, sizeY: Int): Image
}

object ImageOutputs {
  class ShellImageOutput extends ImageOutput {
    import Console._
    val greyScale = List(' ',' ','.',',','-','~',':',';','!','*','=','$','#','@')

    val colors: List[(String, Color)] = List((BLACK, Colors.Black), (RED, Colors.Red), (GREEN, Colors.Green), (YELLOW, Colors.Yellow),
      (BLUE, Colors.Blue), (MAGENTA, Colors.Magenta), (CYAN, Colors.Cyan), (WHITE, Colors.White))

    override def image(sizeX: Int, sizeY: Int): Image = new Image {
      val image: Array[Array[String]] = Array.ofDim(sizeY, sizeX)

      override def write(color: Color, x: Int, y: Int): Unit = {
        val shade = (color.x + color.y + color.z) / 3
        val charIndex: Int = (greyScale.length * shade) toInt
        val closestColor = colors.map(a => (a._1, a._2.distance(color))).foldLeft((BLACK, 1.0))((a, b) => if (a._2 < b._2) a else b)._1

        image(y)(x) = closestColor + greyScale(charIndex)
      }

      override def close(): Unit = {
        image.foreach(line => println(line.mkString))
        println
      }
    }

  }

  class BMPImageOutput(pathPrefix: String = "output/image_") extends ImageOutput {
    var count: Int = 0

    override def image(sizeX: Int, sizeY: Int): Image = new Image {
      val image = new BufferedImage(sizeX, sizeY, BufferedImage.TYPE_INT_RGB)

      override def write(color: Color, x: Int, y: Int): Unit = image.setRGB(x, y, Colors toInt color)
      override def close(): Unit = ImageIO.write(image, "BMP", getFile(pathPrefix + count + ".bmp"))
    }

    def getFile(path: String): File = {
      val file = new File(path)
      file.mkdirs()
      file
    }

  }

  class WindowOutput extends ImageOutput {
    val window: MainFrame = new MainFrame() {
      title = "Ray Tracer"
    }

    override def image(sizeX: Int, sizeY: Int): Image = new Image {
      val image = new BufferedImage(sizeX, sizeY, BufferedImage.TYPE_INT_RGB)

      override def write(color: Color, x: Int, y: Int): Unit = {
        image.setRGB(x, y, Colors.toInt(color))
      }

      override def close(): Unit = {
//        image = new BufferedImage(sizeX, sizeY, BufferedImage.TYPE_INT_RGB)
        if (!window.visible) {
          window.preferredSize = new Dimension(sizeX, sizeY)
          window.contents = new ImageComponent(image, sizeX, sizeY)
          window.visible = true
        }

        window.contents.head.asInstanceOf[ImageComponent].setImage(image)
      }
    }

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