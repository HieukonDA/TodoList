package com.example.todolist.Adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.AddNewTask
import com.example.todolist.R
import com.example.todolist.Tasks
import com.example.todolist.Utils.DatabaseHandler
import com.example.todolist.model.TodoModel

class ToDoAdapter(private val activity: Tasks, private val db: DatabaseHandler) : RecyclerView.Adapter<ToDoAdapter.ViewHolder>() {
    private var todoList: MutableList<TodoModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.task_layout, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        db.openDatabase()
        val item: TodoModel = todoList[position]
        holder.task.text = item.getTask()
        holder.task.isChecked = toBoolean(item.getStatus())
        holder.task.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                db.updateStatus(item.getId(), 1)
            } else {
                db.updateStatus(item.getId(), 0)
            }
        }
    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    private fun toBoolean(n : Int) : Boolean{
        return n!= 0
    }

    public fun setTasks(todoList: MutableList<TodoModel>){
        this.todoList = todoList
        notifyDataSetChanged()
    }
    public fun getContext(): Context {
        return activity
    }

    public fun deleteItem(position: Int){
        val item: TodoModel =todoList.get(position)
        db.deleteTask(item.getId())
        todoList.removeAt(position)
        notifyItemRemoved(position)
    }

    public fun editItem(position: Int){
        val item : TodoModel = todoList.get(position)
        val bundle = Bundle()
        bundle.putInt("id", item.getId())
        bundle.putString("task", item.getTask())
        val fragment = AddNewTask()
        fragment.arguments = bundle
        fragment.show(activity.getSupportFragmentManager(), AddNewTask.TAG)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var task: CheckBox = itemView.findViewById(R.id.todoCheckBox)
    }

}