package com.TrX

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class ActivityResult : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val name : TextView = findViewById(R.id.tvname)
        val score : TextView = findViewById(R.id.Score)
        val btnFinish : Button = findViewById(R.id.btn_Finish)

        name.text = intent.getStringExtra(Constants.User_name)

        val totalQuestion = intent.getIntExtra(Constants.TotalQuestions,0)
        val correctAnswers = intent.getIntExtra(Constants.CorrectAnswers,0)

        score.text = "Your Score is $correctAnswers out OF $totalQuestion"

        btnFinish.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}