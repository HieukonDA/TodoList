package com.example.todolist

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.Adapter.ToDoAdapter
import com.example.todolist.Utils.DatabaseHandler
import com.example.todolist.model.TodoModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class Tasks : AppCompatActivity(), DialogCloseListener {

    private lateinit var calendar_btn: View
    private lateinit var avatar_btn: View
    private lateinit var tasksRecyclerView: RecyclerView
    private lateinit var tasksAdapter: ToDoAdapter
    private lateinit var db: DatabaseHandler
    private lateinit var fab: FloatingActionButton

    private lateinit var taskList : MutableList<TodoModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasks)

        db = DatabaseHandler(this)
        db.openDatabase()

        taskList = ArrayList<TodoModel>()
        tasksRecyclerView = findViewById(R.id.task_recyclerview)

        calendar_btn = findViewById(R.id.calendar_btn)
        avatar_btn = findViewById(R.id.avatar_btn)

        calendar_btn.setOnClickListener{
            startActivity(Intent(this@Tasks, Calendar::class.java))
            overridePendingTransition(0, 0)

            calendar_btn.isSelected = !calendar_btn.isSelected
        }

        avatar_btn.setOnClickListener{
            startActivity(Intent(this@Tasks, Avatar::class.java))
            overridePendingTransition(0, 0)

            calendar_btn.isSelected = !calendar_btn.isSelected
        }

        //recycler view
        tasksRecyclerView.layoutManager = LinearLayoutManager(this)
        tasksAdapter = ToDoAdapter(this@Tasks, db)
        tasksRecyclerView.adapter = tasksAdapter

        fab = findViewById(R.id.fab)

        val itemTouchHelper: ItemTouchHelper = ItemTouchHelper(RecyclerItemTouchHelper(tasksAdapter))
        itemTouchHelper.attachToRecyclerView(tasksRecyclerView)

        taskList = db.getAllTasks().toMutableList()
        taskList.reverse()
        tasksAdapter.setTasks(taskList)

        fab.setOnClickListener {
            AddNewTask.newInstance().show(supportFragmentManager, AddNewTask.TAG)
        }


        val task = TodoModel() // Tạo một đối tượng TodoModel mới
//        task.setTask("this is a test task")
//        task.setStatus(0)
//        task.setId(1)
//
//        taskList.add(task)
//        taskList.add(task)
//        taskList.add(task)
//        taskList.add(task)
//        taskList.add(task)
//        taskList.add(task)
//        taskList.add(task)
//        taskList.add(task)
//        taskList.add(task)
//        taskList.add(task)
//        taskList.add(task)
//        taskList.add(task)
//
        tasksAdapter.setTasks(taskList)
    }

    public fun onClickHandler(view: View){
        var animation:Animation = AnimationUtils.loadAnimation(this, androidx.appcompat.R.anim.abc_fade_in)
        view.startAnimation(animation)
    }

    override fun handleDialogClose(dialog: DialogInterface) {
        taskList = db.getAllTasks().toMutableList()
        taskList.reverse()
        tasksAdapter.setTasks(taskList)
        tasksAdapter.notifyDataSetChanged()
    }
}
