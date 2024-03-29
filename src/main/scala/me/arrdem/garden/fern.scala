package me.arrdem.garden
import me.arrdem.planter.Planter

import scala.math.Pi


object Fern extends Planter
{
  def precomp(steps : Int = 5) = {
    Transition("X" := "F-[[X]+X]+F[+FX]-X")
    Transition("F" := "FF")

    State("[" := DO PUSH() DONE())
    State("]" := DO POP() DONE())
    State("+" := DO TURN(-5.0*Pi/36.0) DONE())    
    State("-" := DO TURN(5.0*Pi/36.0) DONE())
    State("F" := DO DRAW(10) DONE())

    compute("X", steps)
  }
}
