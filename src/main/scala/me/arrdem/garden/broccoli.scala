package me.arrdem.planter
import me.arrdem.turtle._
import me.arrdem.lsystem._
import scala.collection.mutable.{HashMap,LinkedList}
import scala.math.Pi

import java.awt._
import java.awt.geom._
import javax.swing._


object Planter extends JFrame
               with TurtleDSL
               with LSystem[String,Function1[Turtle, Unit]]
{
  /* Define a Planter object which will use both Turtle and LSystem to draw the
   * Nth iteration of a fractal :D
   ***************************************************************************
   */
  def draw(k:String, t:Turtle) =
    if(_invoke_map contains(k))
      _invoke_map.get(k).getOrElse((t:Turtle) => t)(t)

  def drawState(s:Seq[String], t:Turtle) =
    for(_s <- s)
      draw(_s, t)

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

    val t = new Turtle
    val s = Iterator.iterate(initialState)(step).drop(steps-1).next()
    drawState(s, t)
    t;
  }
}
