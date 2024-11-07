package com.uveaa.todo_mvi.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.uveaa.todo_mvi.intent.TodoIntent
import com.uveaa.todo_mvi.model.local.Todo

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MainScreen(list: List<Todo>, onIntent: (TodoIntent) -> Unit) {

    val title = remember { mutableStateOf("") }

    Scaffold(topBar = { TopAppBar(title = { Text(text = "Todo Items") }) }) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            if (list.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Nothing found")
                }
            } else {
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(list) {
                        val isChecked = remember {
                            mutableStateOf(it.isDone)
                        }
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .combinedClickable(enabled = true,
                                    onClick = {},
                                    onLongClick = {
                                        onIntent.invoke(
                                            TodoIntent.Delete(it)
                                        )
                                    })
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 12.dp, vertical = 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = it.title)
                                Checkbox(checked = isChecked.value, onCheckedChange = { check ->
                                    isChecked.value = check
                                    onIntent.invoke(TodoIntent.Update(it.copy(isDone = check)))
                                })
                            }
                            HorizontalDivider()
                        }
                    }
                }
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextField(
                    value = title.value,
                    onValueChange = { title.value = it },
                    modifier = Modifier.fillMaxWidth()
                )
                Button(onClick = {
                    onIntent.invoke(
                        TodoIntent.Insert(
                            Todo(title = title.value, isDone = false, id = 0)
                        )
                    )
                    title.value = ""
                }) {
                    Text(text = "Save todo")
                }
            }
        }
    }
}