package com.example.todolist.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todolist.data.db.entities.ToDoItem

@Database(
    entities = [ToDoItem::class],
    version = 3
)
abstract class ToDoDatabase : RoomDatabase() {
    abstract fun getShoppingDao(): ToDoDao

    companion object {
        @Volatile
        private var instance: ToDoDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                ToDoDatabase::class.java, "ToDoDB.db"
            ).fallbackToDestructiveMigration().build()
    }
}