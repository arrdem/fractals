package me.arrdem.planter
import me.arrdem.turtle._
import me.arrdem.lsystem._


class Planter extends _bitch
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
  def compute(initialState:Seq[String], steps:Int) = {
    val t = new Turtle
    val s = Iterator.iterate(initialState)(step).drop(steps-1).next()
    drawState(s, t)
    t
  }
}
