package template.jtwig

import java.text.SimpleDateFormat

import model.Item
import org.apache.logging.log4j.scala.Logging
import org.specs2.mutable.Specification

import scala.collection.JavaConverters._

class JtwigWriterTest extends Specification with Logging {

  "Jtwig" should {

    val writer = new JtwigWriter

    "writer enable" in {
      val html = writer.write("Hello {{ world }}!", Map("world" -> "new world"))
      logger.info(s"html = $html")
      html must_!== ""
    }
    "validator enable" in {
      val result = writer.validate("HELP {{ who }}!", Map("who" -> "me"))
      logger.info(s"result = $result")
      result must_=== true
    }
    "object write success" in {
      val template: String =
        """
          |オブジェクトを表示します
          |{% if item != null %}
          |ID = {{item.id}}
          |NAME = {{item.name}}
          |ATTR = {{item.attribute}}
          |{% endif %}
        """.stripMargin
      val df = new SimpleDateFormat("yyyy-MM-dd")
      val attr = Map("sex" -> "female", "age" -> 10, "birth" -> df.parse("1999-10-10"))
      val item = Item(1, "test", attr)
      val html = writer.write(template, Map("item" -> item))
      logger.info(s"html = $html")
      html must_!== ""
    }
    "instance write success" in {
      val template: String =
        """
          |ここが開始
          |インスタンスのプリントをします
          |{% if item != null %}
          |  {{ item.id }} : {{ item.name }}
          |  attr - {{ item.attribute }}
          |  {% for key,value in item.attribute %}
          |    {{ key }} = {{ value }}
          |  {% endfor %}
          |{% endif %}
          |ここで終わり
        """.stripMargin
      val df = new SimpleDateFormat("yyyy-MM-dd")
      val attr = Map("sex" -> "female", "age" -> 10, "birth" -> df.parse("1999-10-10"))
      val item = Item(1, "test", attr)
      val html = writer.write(template, Map("item" -> item))
      logger.info(s"html = $html")
      html must_!== ""
    }
    "mapped write success" in {
      val template: String =
        """
          |ここが開始
          |ループします
          |{% for item in items %}
          |  {{ item.id }} : {{ item.name }}
          |  attr - {{ item.attribute }}
          |  {% for key,value in item.attribute %}
          |    {{ key }} = {{ value }}
          |  {% endfor %}
          |{% endfor %}
          |ここで終わり
        """.stripMargin
      val df = new SimpleDateFormat("yyyy-MM-dd")
      val items = List(
        Item(1, "test", Map("sex" -> "female", "age" -> 21, "birth" -> df.parse("1999-10-10"))),
        Item(2, "試験", Map("sex" -> "male", "age" -> 20, "birth" -> df.parse("2000-1-1")))
      )
      val html = writer.write(template, Map("items" -> items.asJava))
      logger.info(s"html = $html")
      html must_!== ""
    }
    "test write success" in {
      val template: String =
        """
          |ここが開始
          |GETTERを使ったインスタンスのプリントをします
          |{% if item != null %}
          |  {{ item.id }} : {{ item.name }}
          |  attr - {{ item.attribute }}
          |  sex = {{ item.attribute.sex }}
          |  age = {{ item.attribute.age }}
          |  birth = {{ item.attribute.birth }}
          |{% endif %}
          |ここで終わり
        """.stripMargin
      val df = new SimpleDateFormat("yyyy-MM-dd")
      val attr = Map("sex" -> "female", "age" -> 10, "birth" -> df.parse("1999-10-10"))
      val item = Item(1, "test", attr)
      val html = writer.write(template, Map("item" -> item))
      logger.info(s"html = $html")
      html must_!== ""
    }
    "error write success" in {
      val template: String =
        """ここが開始
          |GETTERを使ったインスタンスのプリントをします
          |TEST
          |AAAA
          |{% if user != null %}
          |  {{ user.id }} : {{ user.name }}
          |  attr - {{ user.attribute }}
          |  sex = {{ user.attribute.sex }}
          |  age = {{ user.attribute.age }}
          |  birth = {{ user.attribute.birth }}
          |{% endif %}
          |ここで終わり""".stripMargin
      logger.debug(s"template = \n$template")
      val df = new SimpleDateFormat("yyyy-MM-dd")
      val attr = Map("sex" -> "female", "age" -> 10, "birth" -> df.parse("1999-10-10"))
      val item = Item(1, "test", attr)
      val result = writer.validate(template, Map("item" -> item))
      logger.info(s"result = $result")
      result must_!== true
    }
  }

}
