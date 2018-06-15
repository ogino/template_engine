package template.jinjava

import java.time.ZoneId
import java.util.Locale

import com.hubspot.jinjava.{Jinjava, JinjavaConfig}
import template.TemplateWriter

import scala.collection.JavaConverters._

class JinjavaWriter extends TemplateWriter {

  override def print(text: String, params: Map[String, AnyRef]): String = {
    assert(Option(text).nonEmpty, params.nonEmpty)
    val builder = JinjavaConfig.newBuilder()
      .withFailOnUnknownTokens(true)
      .withLocale(Locale.JAPANESE)
      .withTimeZone(ZoneId.of("Asia/Tokyo"))
      .withMaxOutputSize(Long.MaxValue)
      .withMaxRenderDepth(Integer.MAX_VALUE)
      .withNestedInterpretationEnabled(true)
      .withTrimBlocks(true)
      .withLstripBlocks(true)
      .withEnableRecursiveMacroCalls(true)
      .withReadOnlyResolver(false)
    val jinja = new Jinjava(builder.build())
    jinja.render(text, params.asJava)
  }

}
