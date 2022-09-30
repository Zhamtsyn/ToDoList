package com.example.todolist

import android.app.Application
import com.example.todolist.data.db.ToDoDatabase
import com.example.todolist.data.repositories.ToDoRepository
import com.example.todolist.ui.shoppingList.ToDoViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class ToDoApplication : Application(), KodeinAware {
    override val kodein: Kodein = Kodein.lazy {
        import(androidXModule(this@ToDoApplication))
        bind() from singleton { ToDoDatabase(instance()) }
        bind() from singleton { ToDoRepository(instance()) }
        bind() from provider { ToDoViewModelFactory(instance()) }
    }
}