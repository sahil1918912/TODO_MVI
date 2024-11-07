package com.uveaa.todo_mvi.intent

import com.uveaa.todo_mvi.model.local.Todo

sealed class TodoIntent {
    data class Insert(val todo: Todo) : TodoIntent()
    data class Update(val todo: Todo) : TodoIntent()
    data class Delete(val todo: Todo) : TodoIntent()
}