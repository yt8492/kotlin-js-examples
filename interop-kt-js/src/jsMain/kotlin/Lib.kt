@file:JsModule("./lib.js")

external val intNum: Int

external interface Obj {
  val foo: String
  val bar: Array<String>
}

external val obj: Obj
