package com.trx.dagger2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.trx.R
import com.trx.databinding.ActivityDagger2Binding

class Dagger2Activity : AppCompatActivity() {

    private lateinit var binding : ActivityDagger2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDagger2Binding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}