package template

import java.io.{PrintWriter, StringWriter}

import org.apache.logging.log4j.scala.Logger

trait TemplateWriter {

  val logger: Logger = Logger.apply(this.getClass)

  def write(text: String, params: Map[String, AnyRef]) : String = {
    text match {
      case null | "" => ""
      case _ => params match {
        case null => ""
        case _ =>
          try {
            return print(text, params)
          } catch {
            case t:Throwable =>
              val writer = new StringWriter
              t.printStackTrace(new PrintWriter(writer))
              logger.error(writer.toString)
          }
          ""
      }
    }
  }

  def validate(text: String, params: Map[String, AnyRef]) : Boolean = {
    text match {
      case null | "" => false
      case _ => params match {
        case null  => false
        case _ =>
          try {
            print(text, params)
            return true
          } catch {
            case t:Throwable =>
              val writer = new StringWriter
              t.printStackTrace(new PrintWriter(writer))
              logger.error(writer.toString)
          }
          false
      }
    }
  }

  def print(text: String, params: Map[String, AnyRef]) : String

}
