package me.arrdem.garden
import me.arrdem.planter.Planter
import scala.math.Pi


object Dragon extends Planter
{
  def precomp(steps : Int = 5) = {
    Transition("X" := Seq("X", "+", "Y", "F"))
    Transition("Y" := Seq("F", "X", "-", "Y"))

    State("-" := DO TURN(Pi/2.0) DONE())
    State("+" := DO TURN(-1*Pi/2.0) DONE())
    State("F" := DO DRAW(10) DONE())
    State("X" := DO DONE())
    State("Y" := DO DONE())

    var initialState = Seq("F", "X")

    compute(initialState, steps)
  }
}
