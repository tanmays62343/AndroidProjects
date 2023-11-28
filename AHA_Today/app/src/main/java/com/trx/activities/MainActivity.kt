package com.trx.activities

import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.trx.R
import com.trx.databinding.ActivityMainBinding
import com.trx.fragments.HeadLinesFragment

//Our File Follows A Proper MVVM Architecture
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    //Initializing Fragments
    private val headlinesFragment = HeadLinesFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(!isNetworkAvailable()){
            binding.offline.visibility = View.VISIBLE
            return
        }

        //Setting our Landing Fragment
        setupFragment(headlinesFragment)

        //OnClick Listener for our Bottom Navigation Bar
        binding.bottomNavBar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.top_headlines -> {
                    setupFragment(headlinesFragment)
                    true
                }

                R.id.all_news -> {
                    //TODO :(Create an all news Fragment : note - sir told not to do Bogus parameter)
                    Toast.makeText(
                        this, "Hello",
                        Toast.LENGTH_SHORT
                    ).show()
                    true
                }

                else -> {
                    Log.d("BRB", "Error in Bottom Nav Bar")
                    false
                }
            }
        }
    }

    //Method to load fragments
    private fun setupFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(binding.fragmentContainer.id, fragment)
            commit()
        }
    }

    //We are checking weather the internet is available or Not (Learn Later)
    private fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

}