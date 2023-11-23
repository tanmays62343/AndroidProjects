package com.trx

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.trx.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var binding : ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val viewModel = ViewModelProvider(this)[CounterViewModel :: class.java]

        binding?.textView?.text = "Counter: ${viewModel.number}"
        Log.d("DATA_V","v-${viewModel.number}")

        binding?.button?.setOnClickListener {
            viewModel.addOne()
            binding?.textView?.text = "Counter: ${viewModel.number}"
        }
    }
}