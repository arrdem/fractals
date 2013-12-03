package me.arrdem.planter
import me.arrdem.turtle._
import me.arrdem.lsystem._
import scala.collection.mutable.{HashMap,LinkedList}
import scala.math.Pi

import java.awt._
import java.awt.geom._
import javax.swing._


object Sierpinski extends JFrame
               with TurtleDSL
               with LSystem[String,Function1[Turtle, Unit]]
{
  /* Define a Sierpinski object which will use both Turtle and LSystem to draw the
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
    Transition("A" := Seq("B", "-", "A", "-", "B"))
    Transition("B" := Seq("A", "+", "B", "+", "A"))

    State("-" := DO TURN(Pi/3.0) DONE())
    State("+" := DO TURN(-1*Pi/3.0) DONE())
    State("A" := DO DRAW(10) DONE())
    State("B" := DO DRAW(10) DONE())

    var initialState = Seq("A")

    val t = new Turtle
    val s = Iterator.iterate(initialState)(step).drop(steps-1).next()
    drawState(s, t)
    t;
  }
}