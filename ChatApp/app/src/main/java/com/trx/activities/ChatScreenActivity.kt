package com.trx.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.trx.R
import com.trx.adapters.ChatScreenAdapter
import com.trx.databinding.ActivityChatScreenBinding
import com.trx.models.Message

class ChatScreenActivity : AppCompatActivity() {


    private lateinit var binding : ActivityChatScreenBinding

    private var messageList = mutableListOf<Message>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = ChatScreenAdapter(this,messageList)
        binding.scChatScreen.adapter = adapter
    }

}