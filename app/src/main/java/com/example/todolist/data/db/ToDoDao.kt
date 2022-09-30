package com.example.todolist.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.todolist.data.db.entities.ToDoItem

@Dao
interface ToDoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(item: ToDoItem)

    @Delete
    fun delete(item: ToDoItem)

    @Query("SELECT*FROM todo_items")
    fun getAllToDoItems(): LiveData<List<ToDoItem>>
}