package com.example.todolist.other

import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.data.db.entities.ToDoItem
import com.example.todolist.ui.shoppingList.EditFragment
import com.example.todolist.ui.shoppingList.ToDoViewModel
import kotlinx.android.synthetic.main.todo_item.view.*

class ToDoAdapter(
    private val viewModel: ToDoViewModel
) : ListAdapter<ToDoItem, ToDoAdapter.ToDoViewHolder>(Comparator()) {

    inner class ToDoViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        fun bind(holder: ToDoViewHolder, item: ToDoItem) {
            itemView.apply {
                tvTask.text = item.name
                cbItem.isChecked = item.isChecked
                toggleStrikeThrough(tvTask, item.isChecked, holder)

                cbItem.setOnClickListener {
                    toggleStrikeThrough(tvTask, !item.isChecked, holder)
                    item.isChecked = !item.isChecked
                    val sItem = ToDoItem(
                        item.id,
                        item.name,
                        item.isChecked,
                        item.oldName
                    )
                    viewModel.upsert(sItem)
                }

                tvTask.setOnClickListener {
                    val manager =
                        (holder.itemView.context as FragmentActivity).supportFragmentManager
                    manager
                        .beginTransaction()
                        .add(R.id.clMain, EditFragment.newInstance(item))
                        .commit()
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {
        return ToDoViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.todo_item, parent, false
            )
        )
    }

    private fun toggleStrikeThrough(
        tvTask: TextView,
        isChecked: Boolean,
        holder: ToDoAdapter.ToDoViewHolder
    ) {
        if (isChecked) {
            tvTask.paintFlags = tvTask.paintFlags or STRIKE_THRU_TEXT_FLAG
            val colorSelected =
                ContextCompat.getColor(holder.itemView.context, R.color.selected_item_background)
            holder.itemView.clItem.setBackgroundColor(colorSelected)
        } else {
            tvTask.paintFlags = tvTask.paintFlags and STRIKE_THRU_TEXT_FLAG.inv()
            val colorSelected =
                ContextCompat.getColor(holder.itemView.context, R.color.item_background)
            holder.itemView.clItem.setBackgroundColor(colorSelected)
        }
    }

    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {
        holder.bind(holder, getItem(position))
    }

    class Comparator : DiffUtil.ItemCallback<ToDoItem>() {

        override fun areItemsTheSame(oldItem: ToDoItem, newItem: ToDoItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ToDoItem, newItem: ToDoItem): Boolean {
            val result = oldItem.oldName == newItem.name
            newItem.oldName = newItem.name
            return result
        }
    }
}