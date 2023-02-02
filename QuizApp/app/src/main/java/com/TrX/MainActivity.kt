package com.TrX

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val startB: Button = findViewById(R.id.StartBtn)
        val etName :EditText = findViewById(R.id.et_name)

        startB.setOnClickListener{

            if(etName.text.isEmpty()){
                Toast.makeText(this,
                    "Please enter your name", Toast.LENGTH_SHORT).show()
            }
            else{
                val intent = Intent(this,QuizQuestions::class.java)
                startActivity(intent)
                finish()
            }
        }

    }
}