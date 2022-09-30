package com.example.todolist.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_items")
data class ToDoItem(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo(name = "item_name")
    var name: String,
    @ColumnInfo(name = "isChecked")
    var isChecked: Boolean,
    @ColumnInfo(name = "oldName")
    var oldName: String
)