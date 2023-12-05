package com.trx.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.trx.databinding.ActivityMainBinding
import com.trx.models.Message
import com.trx.models.User

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    private val usersList = mutableListOf<User>()

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.btnAddChat.setOnClickListener {
            Intent(this,AddChatActivity::class.java).also {
                startActivity(it)
            }
        }

        binding.Logout.setOnClickListener {
            firebaseAuth.signOut()
            Intent(this,LoginActivity::class.java).also {
                startActivity(it)
            }
            finish()
        }

    }

}