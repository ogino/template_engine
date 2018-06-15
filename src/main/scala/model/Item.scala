package model

import scala.collection.JavaConverters._

case class Item(id: Int, name: String, attribute: java.util.Map[String, Any]) {

  override def toString: String = {
    s"id = $id, name = $name, attribute = $attribute"
  }

}

object Item {
  def apply(id: Int, name: String, attribute: Map[String, Any]): Item = new Item(id, name, attribute.asJava)
}