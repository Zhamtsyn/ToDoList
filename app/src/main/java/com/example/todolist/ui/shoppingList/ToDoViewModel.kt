package com.example.todolist.ui.shoppingList

import androidx.lifecycle.ViewModel
import com.example.todolist.data.db.entities.ToDoItem
import com.example.todolist.data.repositories.ToDoRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ToDoViewModel(
    private val repository: ToDoRepository
) : ViewModel() {

    fun upsert(item: ToDoItem) = CoroutineScope(Dispatchers.IO).launch {
        repository.upsert(item)
    }

    fun delete(item: ToDoItem) = CoroutineScope(Dispatchers.IO).launch {
        repository.delete(item)
    }

    fun getAllToDoItems() = repository.getAllToDoItems()
}