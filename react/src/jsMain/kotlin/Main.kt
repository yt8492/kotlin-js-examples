import react.create
import react.dom.client.createRoot
import web.dom.document

fun main() {
  val root = document.getElementById("root") ?: return
  createRoot(root).render(App.create())
}
