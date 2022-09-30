package com.example.todolist.ui.shoppingList

import com.example.todolist.data.db.entities.ToDoItem

interface DeleteDialogListener {
    fun onDeleteButtonClicked(item: ToDoItem)
}