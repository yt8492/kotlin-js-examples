package com.yt8492.sample.compose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun App(
    viewModel: AppViewModel = viewModel {
        AppViewModel()
    }
) {
    val todos = viewModel.todos.collectAsStateWithLifecycle().value
    MaterialTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text("Todo List")
                    },
                )
            },
        ) { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
            ) {
                items(todos) { todo ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(todo.text)
                        IconButton(
                            onClick = {
                                viewModel.onClickDelete(todo)
                            },
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "delete",
                            )
                        }
                    }
                }
                item {
                    val input = viewModel.inputText.collectAsStateWithLifecycle().value
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        TextField(
                            value = input,
                            singleLine = true,
                            onValueChange = {
                                viewModel.onInputTodo(it)
                            }
                        )
                        IconButton(
                            onClick = {
                                viewModel.onClickAdd()
                            },
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "add",
                            )
                        }
                    }
                }
            }
        }
    }
}
