import kotlin.js.Json

@JsModule("express")
external val express: Express

@JsModule("express")
external fun express(): Application

@JsModule("cors")
external fun cors(): Middleware

external interface Express {
  fun json(): Middleware
}

external interface Middleware

external interface Application {
  fun use(middleware: Middleware)
  fun get(path: String, callback: (Request, Response, NextFunction) -> Unit)
  fun post(path: String, callback: (Request, Response, NextFunction) -> Unit)
  fun delete(path: String, callback: (Request, Response, NextFunction) -> Unit)
  fun put(path: String, callback: (Request, Response, NextFunction) -> Unit)
  fun patch(path: String, callback: (Request, Response, NextFunction) -> Unit)
  fun listen(port: Int, callback: () -> Unit = definedExternally)
}

external interface Request {
  val body: dynamic
  val params: Json
}

fun <T> Request.bodyAs(): T {
  return body
}

external interface Response {
  fun status(code: Int): Response
  fun json(json: Any)
  fun send(body: String)
  fun sendStatus(code: Int)
}

typealias NextFunction = (Any) -> Unit
