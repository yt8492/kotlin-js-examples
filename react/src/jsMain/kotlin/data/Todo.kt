package data

import kotlinx.serialization.Serializable

@Serializable
data class Todo(
  val id: String,
  val text: String,
)
