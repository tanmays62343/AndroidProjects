package com.TrX

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import kotlinx.coroutines.NonCancellable.start

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val startBtn : Button = findViewById(R.id.btn_start)
        val userName : EditText = findViewById(R.id.et_name)

        startBtn.setOnClickListener{

            if(userName.text.isEmpty()){
                Toast.makeText(this, "Please enter your name", Toast.LENGTH_LONG).show()
            }
            else{
                val intent = Intent(this,QuizQuestion::class.java)
                intent.putExtra(Constants.User_name,userName.text.toString())
                startActivity(intent)
                finish()
            }
        }

    }
}