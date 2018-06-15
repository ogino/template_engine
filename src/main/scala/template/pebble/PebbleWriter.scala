package template.pebble

import java.io.StringWriter
import java.util.Locale

import com.mitchellbosecke.pebble.PebbleEngine
import com.mitchellbosecke.pebble.loader.StringLoader
import template.TemplateWriter

import scala.collection.JavaConverters._

class PebbleWriter extends TemplateWriter {

  override def print(text: String, params: Map[String, AnyRef]): String = {
    assert(Option(text).nonEmpty, params.nonEmpty)
    val engine = new PebbleEngine.Builder().loader(new StringLoader())
      .strictVariables(true)
      .allowGetClass(true)
      .cacheActive(false)
      .greedyMatchMethod(true)
      .defaultLocale(Locale.JAPANESE)
      .newLineTrimming(false)
      .ensuring(true)
      .build()
    val template = engine.getTemplate(text)
    val writer = new StringWriter
    template.evaluate(writer, params.asJava)
    writer.toString
  }

}
