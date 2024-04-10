package com.example.todolist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils

class Tasks : AppCompatActivity() {

    private lateinit var calendar_btn: View
    private lateinit var avatar_btn: View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasks)

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
    }

    public fun onClickHandler(view: View){
        var animation:Animation = AnimationUtils.loadAnimation(this, androidx.appcompat.R.anim.abc_fade_in)
        view.startAnimation(animation)
    }


}
