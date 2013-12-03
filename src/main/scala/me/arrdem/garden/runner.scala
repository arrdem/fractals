package me.arrdem.planter
import me.arrdem.turtle._
import me.arrdem.lsystem._
import scala.collection.mutable.{HashMap,LinkedList}
import scala.math.Pi

import java.awt._
import java.awt.geom._
import javax.swing._


object Runner extends JFrame
               with TurtleDSL
               with LSystem[String,Function1[Turtle, Unit]]
{
  /* Main method, defines the L-system & draws it
   ****************************************************************************
   */
  def main(args : Array[String]) = {
    var steps = 5
    try {
	 steps = augmentString(args(1)).toInt;
    } catch {
      case ex : ArrayIndexOutOfBoundsException => {
        println("Iterations not specified using default of 5")
	 } 
	 //check for case where it was specified and was improper
    }
    val t : Turtle = args(0) match {
        case "dragon" => Dragon.precomp(steps)
        case "fern" => Fern.precomp(steps)
        case "koch" => Koch.precomp(steps)
        case "planter" => Planter.precomp(steps)
        case "sierpinski" => Sierpinski.precomp(steps)
        case _ => Planter.precomp(steps)
      }
    SwingUtilities.invokeLater(new Runnable() {
      def run() = {
        setSize(250, 200);
        setTitle("Points");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true)
        add(t);
      }
    });
  }
}