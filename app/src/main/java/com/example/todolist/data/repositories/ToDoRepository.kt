package com.example.todolist.data.repositories

import com.example.todolist.data.db.ToDoDatabase
import com.example.todolist.data.db.entities.ToDoItem

class ToDoRepository(
    private val db: ToDoDatabase
) {
    fun upsert(item: ToDoItem) = db.getShoppingDao().upsert(item)

    fun delete(item: ToDoItem) = db.getShoppingDao().delete(item)

    fun getAllToDoItems() = db.getShoppingDao().getAllToDoItems()
}