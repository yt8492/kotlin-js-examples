package com.yt8492.sample.compose.data

import kotlinx.serialization.Serializable

@Serializable
data class CreateTodoRequest(
  val text: String,
)
