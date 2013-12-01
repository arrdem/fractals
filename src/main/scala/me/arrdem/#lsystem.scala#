package me.arrdem.lsystem
import scala.collection.mutable.{HashMap,LinkedList}

trait LSystem[keytype,fntype] 
{
  /* Define a trait for objects which will behave like an L system
   ***************************************************************************
   */
  var _invoke_map = HashMap[keytype,fntype]()
  var _tr_map = HashMap[keytype,Seq[keytype]]()

  def translate(s:Seq[keytype], k:keytype) : Seq[keytype] = {
    val v = _tr_map.get(k).getOrElse(Seq[keytype](k))
    //println(format("[DEBUG] Mapped %s -> %s", k, v))
    s ++: v
  }

  def step(s:Seq[keytype]) : Seq[keytype] = {
    s.foldLeft(Seq[keytype]())(translate)
  }

  /* Hack out a DSL wrapper for adding states and transitions
   ***************************************************************************
   */

  case class Assignment(s:keytype) 
  {
    def :=(v:Seq[keytype]):Function0[Tuple2[keytype,Seq[keytype]]]
      = (() => (s, v))

    def :=(v:fntype):Function0[Tuple2[keytype,fntype]]
      = (() => (s, v))
  }
  
  implicit def symbol2Assignment(s:keytype) = Assignment(s)

  def Transition(e:Function0[Tuple2[keytype,Seq[keytype]]]) = {
    _tr_map += e()
  }

  def State(e:Function0[Tuple2[keytype,fntype]]) = {
    _invoke_map += e()
  }
}
