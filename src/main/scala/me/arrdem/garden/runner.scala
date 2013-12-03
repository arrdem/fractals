package me.arrdem.garden
import me.arrdem.turtle.Turtle

import java.awt._
import java.awt.geom._
import javax.swing._


object Runner extends JFrame
{
  var halp = """Usage: run [fractal]
    fractals: dragon     -> 15 iterations max
              fern       -> 8  iterations max
              kotch      -> 10 iterations max
              broccoli   -> 12 iterations max
              sierpinski -> 10 iterations max"""

  def main(args : Array[String]) = {

    var steps = 5
    if(args.length > 0) {
      try {
	    steps = augmentString(args(1)).toInt;
      } catch {
        case ex : ArrayIndexOutOfBoundsException =>
          println("Iterations not specified using default of 5")

        case ex : NumberFormatException => {
          println("Could not parse iteration count! aborting...")
          sys.exit(1)
        }
      }

      if(steps > 10)
        println("WARNING: Running more than 10 iterations, this could be a while!")

      val t : Turtle = args(0) match {
        case "dragon"     => Dragon.precomp(steps)
        case "fern"       => Fern.precomp(steps)
        case "koch"       => Koch.precomp(steps)
        case "broccoli"   => Broccoli.precomp(steps)
        case "sierpinski" => Sierpinski.precomp(steps)
        case "-h" | "--help" => {
          println(halp)
          sys.exit(0)
        }
        case _            => Broccoli.precomp(steps)
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

    } else {
      println(halp)
      sys.exit(1)
    }
  }
}
