package com.example.todolist.ui.shoppingList

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatDialog
import com.example.todolist.R
import com.example.todolist.data.db.entities.ToDoItem
import kotlinx.android.synthetic.main.dialog_delete_item.*

class DeleteToDoItemDialog(
    context: Context,
    private var deleteDialogListener: DeleteDialogListener,
    val item: ToDoItem
) :
    AppCompatDialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_delete_item)

        tvYes.setOnClickListener {
            deleteDialogListener.onDeleteButtonClicked(item)
            dismiss()
        }

        tvNo.setOnClickListener {
            cancel()
        }

    }
}