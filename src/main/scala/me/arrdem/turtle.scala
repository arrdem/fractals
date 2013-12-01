package me.arrdem.turtle
import scala.collection.mutable.LinkedList
import scala.collection.mutable.Stack
import scala.math._

import java.awt._
import java.awt.geom._
import javax.swing._

/*
 * The turtle class provides an implementation for the basic turtle graphing
 * system which will be used by LSystems for drawing. Just a simple drawing
 * API...
 *
 * .draw(distance : Integer) : None
 * .turn(dTheta : Integer) : None
 * .getHeading() : Double
 * .setHeading(theta : Double) : None
 * .render() : None
 */

class Turtle extends JPanel {
  /* Define the mutable state drawing operations
   ***************************************************************************
   */
  var heading : Double =  Pi / 2.0;
  var _size = (1024, 1024);                      // basic size of window, px
  var _x : Double = (_size._1 / 2.0)
  var _y : Double = 0.0
  var lines = LinkedList[Line2D]()               // seq of line segments
  var stack = Stack[(Double,Double,Double)]()

  def draw(distance: Int) = {
    /*println(format("[DEBUG] Drawing...   (%f, %f), theta: %f, distance: %d",
            _x,_y,heading,distance))*/
    val xf = _x + (cos(heading) * distance.toDouble)
    val yf = _y + (sin(heading) * distance.toDouble)

    val p1 : Point2D=new Point2D.Double(_x,_y)
    val p2 : Point2D=new Point2D.Double(xf,yf)
    val l : Line2D=new Line2D.Double(p1,p2)

    lines = lines :+ l

    _x = xf; _y = yf
    None;
  }

  def turn(dTheta : Double) = {
    /*println(format("Turning... %f %f", heading, dTheta))*/
    heading += dTheta
    None;
  }

  def pop() = {
    val top = stack.pop()
    /*println(format("[DEBUG] Popping the stack! (%f, %f)", top._2, top._3))*/
    heading = top._1
    _x = top._2
    _y = top._3
  }

  def push() = {
    /*println(format("[DEBUG] Pushing the stack! (%f, %f)", _x, _y))*/
    stack.push((heading, _x, _y))
  }

  def render(g:Graphics) = {

    val g2d : Graphics2D = g.asInstanceOf[Graphics2D]
    g2d.setColor(Color.green)
    g2d.setBackground(Color.black)

    println("[DEBUG] RENDERING --------------------------")

    for(l <- lines) {
      // take care of the initial case
      g2d.draw(l)
    }
  }

  override def paintComponent(g:Graphics) = {
    super.paintComponent(g)
    render(g)
  }
}

class _bitch {}

trait TurtleDSL {
  /* Define an exceedingly simple turtle DSL atop the mutable state drawing
   * operations
   ***************************************************************************
  */

  var _apply_list = LinkedList[Function1[Turtle, Any]]();

  def DO() =
    new _bitch with TurtleDSL

  def DRAW(d:Int) = {
    _apply_list = _apply_list :+ ((t:Turtle) => {t draw(d)})
    this
  }

  def PUSH() = {
    _apply_list = _apply_list :+ ((t:Turtle) => {t push})
    this
  }

  def POP() = {
    _apply_list = _apply_list :+ ((t:Turtle) => {t pop})
    this
  }

  def TURN(i:Double) = {
    _apply_list =
      _apply_list :+ ((t:Turtle) => t turn(i))
    this
  }

  def DONE() =
    (t:Turtle) => apply(t)

  def apply(t: Turtle) = {
    for(f <- _apply_list) {
      f(t)
    }
  }

  def clear = {
    _apply_list = LinkedList[Function1[Turtle, Any]]();
  }
}
