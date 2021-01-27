package com.k3rnl

import com.k3rnl.Animators.{Animated, Animator}
import com.k3rnl.Constants.Colors
import com.k3rnl.Constants.Color
import com.k3rnl.ImageOutputs.WindowOutput
import com.k3rnl.Shaders.Shader
import com.k3rnl.Vectors.Vector3d

case class Scene(val sizeX: Int, val sizeY: Int,
                 var position: Vector3d,
                 val background: Color = Colors.Black,
                 val light: Vector3d,
                 val objects: List[Object],
                 val shaders: List[Shader],
                 val animators: List[Animator[Scene]] = List(),
                 val imageOutput: ImageOutput = new WindowOutput) extends Animated[Scene] {

  def output: Image = imageOutput.image(sizeX, sizeY)

}
