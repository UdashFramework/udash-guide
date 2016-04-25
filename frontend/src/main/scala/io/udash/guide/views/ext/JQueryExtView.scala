package io.udash.guide.views.ext

import io.udash._
import io.udash.guide.{Context, _}
import io.udash.guide.components.CodeBlock
import io.udash.guide.views.{References, Versions}
import org.scalajs.dom

import scalatags.JsDom

case object JQueryExtViewPresenter extends DefaultViewPresenterFactory[JQueryExtState.type](() => new JQueryExtView)


class JQueryExtView extends View {
  import JsDom.all._

  override def getTemplate: dom.Element = div(
    h1("Udash jQuery wrapper"),
    p(
      "The jQuery libarary is a very popular tool in the web development. We have created a strongly typed wrapper for jQuery, ",
      "which allows you to use jQuery in the typed environment of Scala.js."
    ),
    h2("The first steps"),
    p("To start development with the jQuery wrapper add the following line in you frontend module dependencies: "),
    CodeBlock(
      s""""io.udash" %%% "udash-jquery" % "${Versions.udashJQueryVersion}"""".stripMargin
    )(),
    p("The wrapper provides a typed equivalent of the jQuery ", i("$()"), " operator: "),
    CodeBlock(
      s"""import io.udash.wrappers.jquery._
         |import scalatags.JsDom.all._
         |
         |val component = h1("Hello, jQuery!").render
         |
         |val paragraphs = jQ("p")
         |val hello = jQ(component)""".stripMargin
    )(),
    p("Now you can use any jQuery method on these values: "),
    CodeBlock(
      s"""paragraphs.show(1500, EasingFunction.swing)
         |hello.hide(AnimationOptions(
         |  duration = Some(3000),
         |  easing = Some(EasingFunction.linear)
         |))""".stripMargin
    )(),
    h2("jQuery event handlers"),
    p("The below example presents events handling with jQuery wrapper: "),
    CodeBlock(
      s"""val onCallback = (_: Element, _: JQueryEvent) =>
         |  jQ(".demo ul").append(li("This will be added on every click").render)
         |val oneCallback = (_: Element, _: JQueryEvent) =>
         |  jQ(".demo ul").append(li("This will be added only once").render)
         |
         |val content = div(cls := "demo")(
         |  button(id := "click")("Click me"),
         |  ul(),
         |  button(
         |    id := "off",
         |    onclick :+= ((_: Event) => {
         |      jQ(".demo #click")
         |        .off("click", onCallback)
         |        .off("click", oneCallback)
         |      false
         |    })
         |  )("Off")
         |).render
         |
         |jQ(".demo #click")
         |  .on("click", onCallback)
         |  .one("click", oneCallback)""".stripMargin
    )(),
    p(
      "Notice that if you want to use the ", i("off()"), " method, then you have to pass exectly the same object ",
      "that you passed to the method ", i("on()"), " or ", i("one()"), ". Be careful with implicit conversions, ",
      "they create new object every time."
    ),
    h2("jQuery callbacks"),
    p("The wrapper provides alse typed API for the jQuery callbacks mechanism: "),
    CodeBlock(
      s"""val callbacks = jQ.callbacks[js.Function1[(Int, Int), js.Any], (Int, Int)]()
         |callbacks.add((t: (Int, Int)) => {
         |  val (a, b) = t
         |  jQ("#plus").append(li(s"${"$a + $b = ${a + b}"}").render)
         |})
         |callbacks.add((t: (Int, Int)) => {
         |  val (a, b) = t
         |  jQ("#minus").append(li(s"${"$a - $b = ${a - b}"}").render)
         |})
         |callbacks.add((t: (Int, Int)) => {
         |  val (a, b) = t
         |  jQ("#mul").append(li(s"${"$a * $b = ${a * b}"}").render)
         |})
         |callbacks.add((t: (Int, Int)) => {
         |  val (a, b) = t
         |  jQ("#div").append(li(s"${"$a / $b = ${a / b}"}").render)
         |})
         |
         |callbacks.fire(1, 1)
         |callbacks.fire(3, 3)
         |callbacks.fire(7, 4)
         |
         |callbacks.disable()
         |callbacks.fire(1, 2)""".stripMargin
    )(),
    h2("What's next?"),
    p(
      "You can find more information on the wrapper ", a(href := References.udashjQueryWrapperRepo)("GitHub repository"), ". ",
      "It also contains an example application, which presents more ways of working with this wrapper."
    )
  ).render

  override def renderChild(view: View): Unit = {}
}