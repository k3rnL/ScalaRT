package com.k3rnl

import java.awt.Graphics2D
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import scala.swing.{Component, Dimension, MainFrame}

trait ImageOutput extends AutoCloseable {
  def write(color: Int, x: Int, y: Int)
}

object ImageOutputs {
  class ShellImageOutput(sizeX: Int, sizeY: Int) extends ImageOutput {
    var x: Int = 0
    var y: Int = 0

    override def write(color: Int, x: Int, y: Int): Unit = {
      //    x += 1
      //
      //    //    print('X')
      //    if (color > 0) print('X') else print(' ')
      //
      //    if (x == sizeX) {
      //      println()
      //      x = 0
      //      y += 1
      //      if (y == sizeY) {
      //        println("-------")
      //        y = 0
      //      }
      //    }
    }

    override def close(): Unit = ???
  }

  class BMPImageOutput(toFile: String, sizeX: Int, sizeY: Int) extends ImageOutput {
    var x = 0
    var y = 0
    var image = new BufferedImage(sizeX, sizeY, BufferedImage.TYPE_INT_RGB)

    override def write(color: Int, x: Int, y: Int): Unit = {
      image.setRGB(x, y, color)
    }

    override def close(): Unit = {
      ImageIO.write(image, "BMP", new File(toFile))
    }
  }

  class WindowOutput(val sizeX: Int, val sizeY: Int) extends ImageOutput {
    var image = new BufferedImage(sizeX, sizeY, BufferedImage.TYPE_INT_RGB)
    val window: MainFrame = new MainFrame() {
      title = "Ray Tracer"
      preferredSize = new Dimension(sizeX, sizeY)
      contents = new ImageComponent(image, sizeX, sizeY)
    }

    override def write(color: Int, x: Int, y: Int): Unit = {
      image.setRGB(x, y, color)
    }

    override def close(): Unit = {
      window.contents.head.asInstanceOf[ImageComponent].setImage(image)
      image = new BufferedImage(sizeX, sizeY, BufferedImage.TYPE_INT_RGB)
      if (!window.visible)
        window.visible = true
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