package com.k3rnl

object Materials {
  val Mat: Material = Material()
    .withAmbient(0.1)
    .withDiffuse(0.6)
    .withRoughness(20)

  val Metal: Material = Material()
    .withAmbient(0.35)
    .withDiffuse(0.2)
    .withRoughness(1200)
    .withSpecular(0.5)
    .withReflection(0.5)

  val Glass: Material = Material()
    .withRefraction(1.5)
    .withRoughness(1000)
    .withSpecular(0.5)

  case class Material(
                       ambient: Double = 0.0,
                       diffuse: Double = 0.0,
                       roughness: Double = 0.0,
                       specular: Double = 0.0,
                       reflection: Double = 0.0,
                       refraction: Double = 0.0) {


    def withAmbient(ambient: Double): Material = copy(ambient = ambient)
    def withDiffuse(diffuse: Double): Material = copy(diffuse = diffuse)
    def withRoughness(roughness: Double): Material = copy(roughness = roughness)
    def withSpecular(specular: Double): Material = copy(specular = specular)
    def withReflection(reflection: Double): Material = copy(reflection = reflection)
    def withRefraction(refraction: Double): Material = copy(refraction = refraction)
  }
}