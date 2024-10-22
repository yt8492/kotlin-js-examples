package com.yt8492.sample.compose.data

import kotlinx.serialization.Serializable

@Serializable
data class Todo(
  val id: String,
  val text: String,
)
