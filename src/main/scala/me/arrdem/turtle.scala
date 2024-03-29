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
  var _min_x : Double = _x
  var _min_y : Double = _y
  var _max_x : Double = _x
  var _max_y : Double = _y
  var lines = LinkedList[Line2D]()               // seq of line segments
  var stack = Stack[(Double,Double,Double)]()

  def draw(distance: Int) = {
    val xf = _x + (cos(heading) * distance.toDouble)
    val yf = _y + (sin(heading) * distance.toDouble)
    
    if(xf > _max_x) _max_x = xf    
    if(xf < _min_x) _min_x = xf
    if(yf > _max_y) _max_y = yf
    if(yf < _min_y) _min_y = yf

    val p1 : Point2D=new Point2D.Double(_x,_y)
    val p2 : Point2D=new Point2D.Double(xf,yf)
    val l : Line2D=new Line2D.Double(p1,p2)

    lines = lines :+ l

    _x = xf; _y = yf
    None;
  }

  def turn(dTheta : Double) = {
    heading += dTheta
    None;
  }

  def pop() = {
    val top = stack.pop()
    heading = top._1
    _x = top._2
    _y = top._3
  }

  def push() = {
    stack.push((heading, _x, _y))
  }

  def render(g:Graphics) = {
    var incr = 1.0f / lines.length
    var cur_color = 0.0f
    val g2d : Graphics2D = g.asInstanceOf[Graphics2D]

    super.setBackground(Color.black)

    g2d.setColor(Color.green)

	//find min/max
	//transform lines based off of this and the bounding of the window
    
    for(l <- lines) { 
      g2d.setColor(new Color(Color.HSBtoRGB(cur_color,1.0f,0.8f))) 
      cur_color = (cur_color + incr)

      val p1 : Point2D=new Point2D.Double((l.getX1() - _min_x) * getSize().width  / (_max_x - _min_x),
                                          (l.getY1() - _min_y) * getSize().height / (_max_y - _min_y))

      val p2 : Point2D=new Point2D.Double((l.getX2() - _min_x) * getSize().width  / (_max_x - _min_x),
                                          (l.getY2() - _min_y) * getSize().height / (_max_y - _min_y))

	  val new_line : Line2D = new Line2D.Double(p1,p2)
      g2d.draw(new_line)
    }
  }

  override def paintComponent(g:Graphics) = {
    super.setBackground(Color.black)
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
