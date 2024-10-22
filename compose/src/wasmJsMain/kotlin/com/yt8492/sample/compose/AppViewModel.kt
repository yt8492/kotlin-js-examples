package com.yt8492.sample.compose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yt8492.sample.compose.data.CreateTodoRequest
import com.yt8492.sample.compose.data.Todo
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AppViewModel : ViewModel() {
  private val httpClient = HttpClient {
    install(ContentNegotiation) {
      json()
    }
    defaultRequest {
      contentType(ContentType.Application.Json)
    }
  }

  private val _todos = MutableStateFlow(listOf<Todo>())
  val todos: StateFlow<List<Todo>> = _todos.asStateFlow()

  private val _inputText = MutableStateFlow("")
  val inputText: StateFlow<String> = _inputText.asStateFlow()

  init {
    viewModelScope.launch {
      getTodos()
    }
  }

  private fun getTodos() {
    viewModelScope.launch {
      val response = httpClient.get("http://localhost:8080/todos")
      _todos.value = response.body()
    }
  }

  fun onClickDelete(todo: Todo) {
    viewModelScope.launch {
      httpClient.delete("http://localhost:8080/todos/${todo.id}")
      getTodos()
    }
  }

  fun onInputTodo(text: String) {
    _inputText.value = text
  }

  fun onClickAdd() {
    viewModelScope.launch {
      val request = CreateTodoRequest(inputText.value)
      httpClient.post("http://localhost:8080/todos") {
        setBody(request)
      }
      _inputText.value = ""
      getTodos()
    }
  }
}
