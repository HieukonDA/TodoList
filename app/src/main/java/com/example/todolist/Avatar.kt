package com.example.todolist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class Avatar : AppCompatActivity() {

    private lateinit var calendar_btn: View
    private lateinit var task_btn: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_avatar)

        task_btn =findViewById(R.id.task_btn)
        calendar_btn = findViewById(R.id.calendar_btn)

        task_btn.setOnClickListener{
            startActivity(Intent(this@Avatar, Tasks::class.java))
        }
        calendar_btn.setOnClickListener{
            startActivity(Intent(this@Avatar, Calendar::class.java))
        }
    }
}