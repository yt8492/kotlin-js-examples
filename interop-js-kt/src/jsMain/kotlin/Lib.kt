@JsExport
fun greeting() {
  println("Hello!")
}

@JsExport
@JsName("greetingWithName")
fun greeting(name: String) {
  println("Hello $name!")
}
