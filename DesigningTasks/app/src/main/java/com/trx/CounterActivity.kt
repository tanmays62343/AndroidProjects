package com.trx

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.trx.databinding.ActivityCounterBinding

class CounterActivity : AppCompatActivity() {

    var binding : ActivityCounterBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCounterBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val viewModel = ViewModelProvider(this)[CounterViewModel :: class.java]

        binding?.button?.setOnClickListener {
            viewModel.addOne()
            binding?.textView?.text = viewModel.number.toString()
        }

    }
}