package me.arrdem.garden
import me.arrdem.planter.Planter
import scala.math.Pi

object Broccoli extends Planter
{
  def precomp(steps : Int = 5) = {
    Transition("0" := "1[0]0")
    Transition("1" := "11")

    State("[" := DO PUSH() TURN(Pi/4.0) DONE())
    State("]" := DO POP() TURN(-1*Pi/4.0) DONE())
    State("0" := DO DRAW(1) DONE())
    State("1" := DO DRAW(1) DONE())

    compute("0", steps)
  }
}
