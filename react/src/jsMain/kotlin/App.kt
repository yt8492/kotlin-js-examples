import data.CreateTodoRequest
import data.Todo
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import react.FC
import react.dom.html.ReactHTML
import react.useCallback
import react.useEffectOnce
import react.useState

private val httpClient = HttpClient {
  install(ContentNegotiation) {
    json()
  }
  defaultRequest {
    contentType(ContentType.Application.Json)
  }
}

val App = FC {
  val (todos, setTodos) = useState {
    listOf<Todo>()
  }
  val getTodos = useCallback(Unit) {
    MainScope().launch {
      val response = httpClient.get("http://localhost:8080/todos")
      val body = response.body<List<Todo>>()
      setTodos(body)
    }
  }
  useEffectOnce {
    getTodos()
  }
  ReactHTML.header {
    ReactHTML.h1 {
      + "Todo List"
    }
  }
  ReactHTML.main {
    todos.forEach { todo ->
      ReactHTML.div {
        + todo.text
        id = todo.id
        ReactHTML.button {
          + "-"
          onClick = {
            MainScope().launch {
              httpClient.delete("http://localhost:8080/todos/${todo.id}")
              getTodos()
            }
          }
        }
      }
    }
    val (newTodoText, setNewTodoText) = useState("")
    ReactHTML.div {
      ReactHTML.input {
        onChange = {
          val text = it.target.value
          setNewTodoText(text)
        }
      }
      ReactHTML.button {
        + "+"
        onClick = {
          MainScope().launch {
            httpClient.post("http://localhost:8080/todos") {
              setBody(CreateTodoRequest(newTodoText))
            }
            setNewTodoText("")
            getTodos()
          }
        }
      }
    }
  }
}
