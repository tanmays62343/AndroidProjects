package com.trx.dobtohourscalculator

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private var TVSelectedDate : TextView? = null
    private var TVAgeInMinutes : TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val DateSelector : Button = findViewById(R.id.DateSelector)
        TVSelectedDate = findViewById(R.id.TVSelectedDate)
        TVAgeInMinutes = findViewById(R.id.TVHousDisplay)

        DateSelector.setOnClickListener {
            DateSelector()
        }
    }

    private fun DateSelector(){
        val MyCalendar = Calendar.getInstance()
        val Year = MyCalendar.get(Calendar.YEAR)
        val Month = MyCalendar.get(Calendar.MONTH)
        val Day = MyCalendar.get(Calendar.DAY_OF_MONTH)
        DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener {_,Year,Month,DayOfMonth ->
                val SelectedDate = "$DayOfMonth/${Month+1}/$Year"
                TVSelectedDate?.text = SelectedDate
                val sdf = SimpleDateFormat("dd/MM/yyyy")
                val Date = sdf.parse(SelectedDate)
                val SelectedDateInMinutes = Date.time/60000
                val CurrentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
                val CurrentDateInMinutes = CurrentDate.time/60000
                val DiffrenceInMinutes = CurrentDateInMinutes - SelectedDateInMinutes
                TVAgeInMinutes?.text = DiffrenceInMinutes.toString()
            },
            Year,
            Month,
            Day
        ).show()
        Toast.makeText(this,"Button Pressed",Toast.LENGTH_LONG).show()
    }

}