package me.arrdem.planter
import me.arrdem.turtle._
import me.arrdem.lsystem._


class Planter extends _bitch
    with TurtleDSL
    with LSystem[String,Function1[Turtle, Unit]]
{
  def draw(k:String, t:Turtle) =
    if(_invoke_map contains(k))
      _invoke_map.get(k).getOrElse((t:Turtle) => t)(t)

  def drawState(s:Seq[String], t:Turtle) =
    for(_s <- s)
      draw(_s, t)

  def compute(initialState:Seq[String], steps:Int) = {
    val t = new Turtle
    val s = Iterator.iterate(initialState)(step).drop(steps-1).next()
    drawState(s, t)
    t
  }
}
