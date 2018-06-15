package template.jinjava

import java.text.SimpleDateFormat
import java.util

import ch.miyabi.model.JItem
import org.apache.logging.log4j.scala.Logging
import org.specs2.mutable.Specification

class JinjavaWriterTest extends Specification with Logging {

  "Jinjava" should {

    val writer = new JinjavaWriter

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
          | {% for key,value in item.attribute.items() %}
          |   {{ key }} : {{ value }}
          | {% endfor %}
          |{% endif %}
        """.stripMargin
      val df = new SimpleDateFormat("yyyy-MM-dd")
      val attr = new java.util.LinkedHashMap[String, Object]() {{
        put("sex", "female")
        put("age", Integer.valueOf(10))
        put("birth", df.parse("1999-10-10"))
      }}
      val item = new JItem(10000, "Java Instance", attr)
      val html = writer.write(template, Map("item" -> item))
      logger.info(s"html = $html")
      html must_!== ""
    }
    "instance write success" in {
      val template: String =
        """
          |ここが開始
          |インスタンスのプリントをします
          |{% if item %}
          | item -> {{ item }}
          |  {{ item.id }} : {{ item.name }}
          |  attr - {{ item.attribute }}
          |  {% for key,value in item.attribute.items() %}
          |    {{ key }} : {{ value }}
          |  {% endfor %}
          |{% endif %}
          |ここで終わり
        """.stripMargin
      val df = new SimpleDateFormat("yyyy-MM-dd")
      val attr = new java.util.LinkedHashMap[String, Object]() {{
        put("sex", "female")
        put("age", Integer.valueOf(10))
        put("birth", df.parse("1999-10-10"))
      }}
      val item = new JItem(1, "INSTANCE TEST CLASS", attr)
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
          | item -> {{ item }}
          |  {{ item.id }} : {{ item.name }}
          |  attr - {{ item.attribute }}
          |  {% for key,value in item.attribute.items() %}
          |    {{ key }} : {{ value }}
          |  {% endfor %}
          |{% endfor %}
          |ここで終わり
        """.stripMargin
      val df = new SimpleDateFormat("yyyy-MM-dd")
      val items = new util.ArrayList[JItem]() {{
        add(new JItem(1, "Testing", new java.util.LinkedHashMap[String, Object]() {{
          put("sex", "female")
          put("age", Integer.valueOf(21))
          put("brith", df.parse("1998-10-10"))
        }}))
        add(new JItem(2, "試験", new java.util.LinkedHashMap[String, Object]() {{
          put("sex", "male")
          put("age", Integer.valueOf(20))
          put("birth", df.parse("1999-10-10"))
        }}))
      }}
      val html = writer.write(template, Map("items" -> items))
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
      val attr = new java.util.LinkedHashMap[String, Object]() {{
        put("sex", "female")
        put("age", Integer.valueOf(10))
        put("birth", df.parse("1999-10-10"))
      }}
      val item = new JItem(1, "test", attr)
      val html = writer.write(template, Map("item" -> item))
      logger.info(s"html = $html")
      html must_!== ""
    }
    "error write success" in {
      val template: String =
        """
          |ここが開始
          |
          |
          |
          |GETTERを使ったインスタンスのプリントをします
          |{% if user != null %}
          |  {{ user.id }} : {{ user.name }}
          |  attr - {{ user.attribute }}
          |  sex = {{ user.attribute.sex }}
          |  age = {{ user.attribute.age }}
          |  birth = {{ user.attribute.birth }}
          |{% endif %}
          |ここで終わり
        """.stripMargin
      logger.debug(s"template = \n$template")
      val df = new SimpleDateFormat("yyyy-MM-dd")
      val attr = new java.util.LinkedHashMap[String, Object]() {{
        put("sex", "female")
        put("age", Integer.valueOf(10))
        put("birth", df.parse("1999-10-10"))
      }}
      val item = new JItem(1, "test", attr)
      val result = writer.validate(template, Map("item" -> item))
      logger.info(s"result = $result")
      result must_!== true
    }
  }
}
