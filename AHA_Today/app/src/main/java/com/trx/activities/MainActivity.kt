package com.trx.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.trx.R
import com.trx.api.NewsService
import com.trx.databinding.ActivityMainBinding
import com.trx.databinding.FragmentHeadLinesBinding
import com.trx.fragments.HeadLinesFragment
import com.trx.models.News
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val headlinesFragment = HeadLinesFragment()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction().apply {
            replace(binding.fragmentContainer.id,headlinesFragment)
            commit()
        }

    }

}