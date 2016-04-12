package io.udash.guide.views.bootstrapping

import io.udash.core.{DefaultViewPresenterFactory, View}
import io.udash.guide.{Context, _}
import io.udash.guide.components.CodeBlock
import io.udash.guide.styles.partials.GuideStyles
import org.scalajs.dom

import scalatags.JsDom
import scalacss.ScalatagsCss._

case object BootstrappingRpcViewPresenter extends DefaultViewPresenterFactory[BootstrappingRpcState.type](() => new BootstrappingRpcView)

class BootstrappingRpcView extends View {
  import Context._

  import JsDom.all._

  override def getTemplate: dom.Element = div(
    h2("Bootstrapping RPC interfaces"),
    p(
      "Creating RPC interfaces for the Udash application is pretty simple. Inside the ", i("shared"),
      " module, define two traits: "
    ),
    ul(GuideStyles.defaultList)(
      li("MainClientRPC extending ", i("ClientRPC"), " - contains methods which can be called by a server on a client application"),
      li("MainServerRPC extending ", i("RPC"), " - contains methods which can be called by a client on a server.")
    ),
    p(
      "That is all you have to do in the ", i("shared"), " module. Implementation of those interfaces will be covered in ",
      a(href := BootstrappingBackendState.url)("backend"), " and ", a(href := BootstrappingFrontendState.url)("frontend"),
      " bootstrapping chapters."
    ),
    h3("RPC vs ClientRPC"),
    p(
      i("RPC"), " is a marker trait for all RPC interfaces. A RPC interface is a trait or class " +
      "whose abstract methods will be interpreted as remote methods by the RPC framework. Remote methods must be defined " +
      "according to following rules:"
    ),
    ul(GuideStyles.defaultList)(
      li("Types of arguments must be serializable by the uPickle library"),
      li("Return type must be either Unit or Future[T] where T is a type serializable by the uPickle library or another RPC interface"),
      li("Method must not have type parameters.")
    ),
    p(
      "RPC interfaces may also have non-abstract members - these will be invoked locally. However, they may invoke " +
      "remote members in their implementations."
    ),
    p(
      i("ClientRPC"), " is a marker trait for all client-side RPC interfaces. It is basically the same as ",
      i("RPC"), ", but it cannot contain abstract methods returning Future[T]. The reason for this is that ",
      "those methods can be called on many clients and there is no standard way of collecting the results. Collecting the results ",
      "can be implemented with one call from the server to the clients and another call from the clients to the server."
    ),
    h4("Examples"),
    p("Example of RPC interfaces:"),
    CodeBlock(
      """trait MainClientRPC extends ClientRPC {
        |  def pong(id: Int): Unit
        |}""".stripMargin)(),
    CodeBlock(
      """trait MainServerRPC extends RPC {
        |  def ping(id: Int): Unit
        |  def hello(name: String): Future[String]
        |}""".stripMargin)(),
    h2("What's next?"),
    p(
      "When RPC interfaces are ready, it is time to bootstrap the ", a(href := BootstrappingBackendState.url)("server-side"),
      " of the application. You can also read more about ", a(href := RpcIntroState.url)("RPC in Udash"), "."
    )
  ).render

  override def renderChild(view: View): Unit = ()
}