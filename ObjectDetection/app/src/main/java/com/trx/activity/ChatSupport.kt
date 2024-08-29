package com.trx.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.trx.R
import com.trx.databinding.ActivityChatSupportBinding
import io.intercom.android.sdk.Intercom

class ChatSupport : AppCompatActivity() {

    private lateinit var binding : ActivityChatSupportBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatSupportBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Register an anonymous user
        //Intercom.client().registerUnidentifiedUser()

        binding.btnChat.setOnClickListener {
            Log.d("BRB", "Button Clicked")
            Intercom.client().displayMessenger()
        }

    }
}