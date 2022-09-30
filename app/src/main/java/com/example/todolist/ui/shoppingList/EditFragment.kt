package com.example.todolist.ui.shoppingList

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.example.todolist.R
import com.example.todolist.data.db.ToDoDatabase
import com.example.todolist.data.db.entities.ToDoItem
import com.example.todolist.data.repositories.ToDoRepository
import kotlinx.android.synthetic.main.todo_item.*
import kotlinx.android.synthetic.main.fragment_edit.*
import kotlinx.android.synthetic.main.todo_item.view.*

class EditFragment(val item: ToDoItem) : Fragment() {
    lateinit var viewModel: ToDoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val database = ToDoDatabase(activity as ToDoActivity)
        val repository = ToDoRepository(database)
        val factory = ToDoViewModelFactory(repository)
        val fragmentManager = (activity as FragmentActivity).supportFragmentManager

        viewModel = ViewModelProvider(this, factory)[ToDoViewModel::class.java]
        if (item.id != null) etEdit.setText(item.name)

        etEdit.requestFocus()
        etEdit.showSoftKeyboard()

        ivBack.setOnClickListener {
            fragmentManager
                .beginTransaction()
                .remove(this)
                .commit()

            it.hideKeyboard()
        }

        ivDelete.setOnClickListener {
            context?.let { it1 ->
                DeleteToDoItemDialog(
                    it1,
                    object : DeleteDialogListener {
                        override fun onDeleteButtonClicked(item: ToDoItem) {
                            viewModel.delete(item)
                            fragmentManager
                                .beginTransaction()
                                .remove(this@EditFragment)
                                .commit()
                        }
                    }, item
                ).show()
            }
        }

        ivDone.setOnClickListener { view1 ->
            if (etEdit.text.isEmpty()) {
                Toast.makeText(context, "Це поле не може бути пустим", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            item.oldName = item.name
            item.name = etEdit.text.toString()
            val item = ToDoItem(item.id, item.name, item.isChecked, item.oldName)
            viewModel.upsert(item)

            fragmentManager
                .beginTransaction()
                .remove(this)
                .commit()

            view1.hideKeyboard()
        }
    }

    private fun EditText.showSoftKeyboard() {
        (this.context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager)
            .showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun View.hideKeyboard() {
        val hide = context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        hide.hideSoftInputFromWindow(windowToken, 0)
    }

    companion object {
        fun newInstance(item: ToDoItem) = EditFragment(item)
    }

}