package me.arrdem.garden
import me.arrdem.planter.Planter
import scala.math.Pi


object Dragon extends Planter
{
  def precomp(steps : Int = 5) = {
    Transition("X" := "X+YF")
    Transition("Y" := "FX-Y")

    State("-" := DO TURN(Pi/2.0) DONE())
    State("+" := DO TURN(-1*Pi/2.0) DONE())
    State("F" := DO DRAW(10) DONE())
    State("X" := DO DONE())
    State("Y" := DO DONE())

    compute("FX", steps)
  }
}
