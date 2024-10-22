package data

import kotlinx.serialization.Serializable

@Serializable
data class CreateTodoRequest(
  val text: String,
)
