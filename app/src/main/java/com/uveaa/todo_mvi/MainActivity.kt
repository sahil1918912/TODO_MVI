package com.uveaa.todo_mvi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.uveaa.todo_mvi.intent.TodoIntent
import com.uveaa.todo_mvi.model.repository.TodoRepository
import com.uveaa.todo_mvi.view.MainScreen
import com.uveaa.todo_mvi.view.theme.TODO_MVITheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var repository: TodoRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TODO_MVITheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Surface(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
                        val listState = repository.getAllTodoList()
                            .collectAsState(initial = emptyList())

                        val scope = rememberCoroutineScope()

                        MainScreen(list = listState.value) { intent ->
                            when (intent){
                                is TodoIntent.Delete -> scope.launch(Dispatchers.IO){
                                    repository.delete(intent.todo)
                                }
                                is TodoIntent.Insert ->  scope.launch(Dispatchers.IO){
                                    repository.insert(intent.todo)
                                }
                                is TodoIntent.Update ->  scope.launch(Dispatchers.IO){
                                    repository.update(intent.todo)
                                }
                            }
                        }

                    }
                }

            }
        }
    }
}
