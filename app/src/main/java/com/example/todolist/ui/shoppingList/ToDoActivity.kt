package com.example.todolist.ui.shoppingList

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.R
import com.example.todolist.data.db.entities.ToDoItem
import com.example.todolist.other.ToDoAdapter
import kotlinx.android.synthetic.main.activity_todo.*
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance
import org.kodein.di.android.kodein

class ToDoActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()
    private val factory: ToDoViewModelFactory by instance()

    private lateinit var viewModel: ToDoViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo)

        viewModel = ViewModelProvider(this, factory)[ToDoViewModel::class.java]

        val adapter = ToDoAdapter(viewModel)

        rvToDoItems.layoutManager = LinearLayoutManager(this)
        rvToDoItems.adapter = adapter

        viewModel.getAllToDoItems().observe(this, {
            adapter.submitList(it)
            if (it.isEmpty()) tvNoTasks.visibility =
                View.VISIBLE else tvNoTasks.visibility = View.GONE
        })

        fab.setOnClickListener {
            supportFragmentManager
                .beginTransaction()
                .add(
                    R.id.clMain, EditFragment.newInstance(
                        ToDoItem(
                            null, "", false, ""
                        )
                    )
                )
                .commit()
        }
    }
}