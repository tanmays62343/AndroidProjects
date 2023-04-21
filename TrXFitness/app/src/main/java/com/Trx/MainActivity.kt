package com.Trx

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.Toast
import com.Trx.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var binding : ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

       // val FstartButton : FrameLayout = findViewById(R.id.Fstart)  (we are using view binding)

        binding?.Fstart?.setOnClickListener() {
            Toast.makeText(this, "Let's Go", Toast.LENGTH_LONG).show()
            var intent = Intent(this, ExcerciseActivity::class.java)
            startActivity(intent)
        }
        binding?.FBMI?.setOnClickListener {
            var intent = Intent(this, BMI_Activity::class.java)
            startActivity(intent)
        }
        binding?.FHistory?.setOnClickListener {
            var intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}