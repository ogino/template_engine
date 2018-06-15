package template.liqp

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import liqp.{RenderSettings, Template}
import template.TemplateWriter

class LiqpWriter extends TemplateWriter {

  override def print(text: String, params: Map[String, AnyRef]): String = {
    assert(Option(text).nonEmpty, params.nonEmpty)
    val settings = new RenderSettings.Builder().withStrictVariables(true).build()
    val template = Template.parse(text).withRenderSettings(settings)
    val mapper = new ObjectMapper
    mapper.registerModule(DefaultScalaModule)
    val json = mapper.writeValueAsString(params)
    logger.debug(s"json = $json")
    template.render(json)
  }

}
