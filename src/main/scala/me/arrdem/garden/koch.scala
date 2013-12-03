package me.arrdem.garden
import me.arrdem.planter.Planter
import scala.math.Pi


object Koch extends Planter
{
  def precomp(steps : Int = 5) = {
    Transition("F" := Seq("F", "+", "F", "-", "F", "-", "F", "+", "F"))

    State("-" := DO TURN(Pi/2.0) DONE())
    State("+" := DO TURN(-1*Pi/2.0) DONE())
    State("F" := DO DRAW(10) DONE())

    var initialState = Seq("F")

    compute(initialState, steps)
  }
}
