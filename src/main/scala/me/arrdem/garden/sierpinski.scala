package me.arrdem.garden
import me.arrdem.planter.Planter

import scala.math.Pi


object Sierpinski extends Planter
{
  def precomp(steps : Int = 5) = {
    Transition("A" := "B-A-B")
    Transition("B" := "A+B+A")

    State("-" := DO TURN(Pi/3.0) DONE())
    State("+" := DO TURN(-1*Pi/3.0) DONE())
    State("A" := DO DRAW(10) DONE())
    State("B" := DO DRAW(10) DONE())

    compute("A", steps)
  }
}
