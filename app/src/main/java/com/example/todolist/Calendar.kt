package com.example.todolist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.Adapter.CalendarAdapter
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

class Calendar : AppCompatActivity(), CalendarAdapter.OnItemListener {

    private lateinit var task_btn: View
    private lateinit var avatar_btn: View

    private lateinit var monthYearText: TextView
    private lateinit var calendarRecyclerView: RecyclerView
    private lateinit var selectedDate: LocalDate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        initWidgets()
        selectedDate = LocalDate.now()
        setMonthView()

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

    private fun initWidgets() {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView)
        monthYearText = findViewById(R.id.monthYearTV)
    }

    private fun setMonthView() {
        monthYearText.text = monthYearFromDate(selectedDate)
        val daysInMonth = daysInMonthArray(selectedDate)
        val calendarAdapter = CalendarAdapter(daysInMonth, this)
        val layoutManager = GridLayoutManager(applicationContext, 7)
        calendarRecyclerView.layoutManager = layoutManager
        calendarRecyclerView.adapter = calendarAdapter
    }

    private fun daysInMonthArray(date: LocalDate): ArrayList<String> {
        val daysInMonthArray = ArrayList<String>()
        val yearMonth = YearMonth.from(date)
        val daysInMonth = yearMonth.lengthOfMonth()
        val firstOfMonth = selectedDate.withDayOfMonth(1)
        val dayOfWeek = firstOfMonth.dayOfWeek.value
        for (i in 1..42) {
            daysInMonthArray.add(
                if (i <= dayOfWeek || i > daysInMonth + dayOfWeek) "" else (i - dayOfWeek).toString()
            )
        }
        return daysInMonthArray
    }

    private fun monthYearFromDate(date: LocalDate): String {
        val formatter = DateTimeFormatter.ofPattern("MMMM yyyy")
        return date.format(formatter)
    }

    fun previousMonthAction(view: View?) {
        selectedDate = selectedDate.minusMonths(1)
        setMonthView()
    }

    fun nextMonthAction(view: View?) {
        selectedDate = selectedDate.plusMonths(1)
        setMonthView()
    }

    override fun onItemClick(position: Int, dayText: String) {
        if (dayText != "") {
            val message = "Selected Date $dayText ${monthYearFromDate(selectedDate)}"
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        }
    }
}