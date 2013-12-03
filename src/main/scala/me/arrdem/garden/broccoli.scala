package me.arrdem.garden
import me.arrdem.planter.Planter
import scala.math.Pi

object Broccoli extends Planter
{
  /* Main method, defines the L-system & draws it
   ****************************************************************************
   */
  def precomp(steps : Int = 5) = {
    Transition("0" := Seq("1", "[", "0", "]", "0"))
    Transition("1" := Seq("1", "1"))

    State("[" := DO PUSH() TURN(Pi/4.0) DONE())
    State("]" := DO POP() TURN(-1*Pi/4.0) DONE())
    State("0" := DO DRAW(1) DONE())
    State("1" := DO DRAW(1) DONE())

    var initialState = Seq("0");

    compute(initialState, steps)
  }
}
