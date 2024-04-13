
package com.example.todolist.Utils

//import android.content.ContentValues
//import android.database.Cursor
//import android.database.sqlite.SQLiteDatabase
//import android.database.sqlite.SQLiteOpenHelper
//import com.example.todolist.model.TodoModel
//import kotlinx.coroutines.flow.internal.NoOpContinuation.context
//import kotlin.coroutines.jvm.internal.CompletedContinuation.context
//import kotlinx.coroutines.flow.internal.NoOpContinuation as NoOpContinuation1

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.todolist.model.TodoModel

class DatabaseHandler(context: Context) : SQLiteOpenHelper(context, NAME, null, VERSION) {
    companion object {
        private const val VERSION = 1
        private const val NAME = "todoList"
        private const val TODO_TABLE = "todo"
        private const val ID = "id"
        private const val TASK = "task"
        private const val STATUS = "status"
        private const val CREATE_TODO_TABLE =
            "CREATE TABLE $TODO_TABLE ($ID INTEGER PRIMARY KEY AUTOINCREMENT, $TASK TEXT, $STATUS INTEGER)"
    }

    private lateinit var db: SQLiteDatabase

    init {
        db = this.writableDatabase
    }

//        private var DatabaseHandler(context : Context)
//        {
//            super(context, NAME, null, VERSION)
//        }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TODO_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Xóa bảng cũ nếu tồn tại
        db.execSQL("DROP TABLE IF EXISTS $TODO_TABLE")

        // Tạo lại các bảng
        onCreate(db)
    }

    fun openDatabase() {
        db = this.writableDatabase
    }

    fun insertTask(task: TodoModel) {
        val cv = ContentValues()
        cv.put(TASK, task.getTask())
        cv.put(STATUS, 0)
        db.insert(TODO_TABLE, null, cv)

    }

    @SuppressLint("Range")
    fun getAllTasks(): List<TodoModel> {
        val taskList: MutableList<TodoModel> = ArrayList()
        var cur: Cursor? = null
        db.beginTransaction()
        try {
            cur = db.query(TODO_TABLE, null, null, null, null, null, null, null)
            if (cur != null) {
                if (cur.moveToFirst()) {
                    do {
                        val task = TodoModel()
                        task.setId(cur.getInt(cur.getColumnIndex(ID)))
                        task.setTask(cur.getString(cur.getColumnIndex(TASK)))
                        task.setStatus(cur.getInt(cur.getColumnIndex(STATUS)))
                        taskList.add(task)
                    } while (cur.moveToNext())
                }
            }
        } finally {
            db.endTransaction()
            if (cur != null) {
                cur.close()
            }
        }
        return taskList
    }
    fun updateStatus(id:Int, status: Int){
        val cv = ContentValues()
        cv.put(STATUS, status)
        db.update(TODO_TABLE, cv, "$ID=?", arrayOf(id.toString()))
    }

    fun updateTask(id:Int, task: String){
        val cv = ContentValues()
        cv.put(TASK, task)
        db.update(TODO_TABLE, cv, "$ID=?", arrayOf(id.toString()))
    }

    fun deleteTask(id: Int)
    {
        db.delete(TODO_TABLE, "$ID=?", arrayOf(id.toString()))
    }
}
