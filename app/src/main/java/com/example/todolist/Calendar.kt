package com.example.todolist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class Calendar : AppCompatActivity() {

    private lateinit var task_btn: View
    private lateinit var avatar_btn: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        task_btn =findViewById(R.id.task_btn)
        avatar_btn = findViewById(R.id.avatar_btn)

        task_btn.setOnClickListener{
            startActivity(Intent(this@Calendar, Tasks::class.java))
            overridePendingTransition(0, 0)
        }
        avatar_btn.setOnClickListener{
            startActivity(Intent(this@Calendar, Avatar::class.java))
            overridePendingTransition(0, 0)
        }
    }
}