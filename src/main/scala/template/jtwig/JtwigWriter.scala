package template.jtwig

import org.jtwig.environment.EnvironmentConfigurationBuilder
import org.jtwig.{JtwigModel, JtwigTemplate}
import template.TemplateWriter

import scala.collection.JavaConverters._

class JtwigWriter extends TemplateWriter {

  override def print(text: String, params: Map[String, AnyRef]): String = {
    assert(Option(text).nonEmpty, params.nonEmpty)
    val configuration = EnvironmentConfigurationBuilder.configuration()
      .parser().withoutTemplateCache().and()
      .render().withStrictMode(true).withStrictMode(true).and()
      .build()
    val template = JtwigTemplate.inlineTemplate(text, configuration)
    val model = JtwigModel.newModel(params.asJava)
    template.render(model)
  }
}
