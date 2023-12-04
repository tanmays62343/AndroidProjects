package com.trx.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.trx.adapters.ChatScreenAdapter
import com.trx.databinding.ActivityChatScreenBinding
import com.trx.models.Message

class ChatScreenActivity : AppCompatActivity() {


    private lateinit var binding : ActivityChatScreenBinding

    private var messageList = mutableListOf<Message>()

    private var messageRoom : String? = null

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var dbRef : DatabaseReference

    /*Can Give the URL Explicitly if it is initialized Wrongly
    or can Re Download Json File and paste it in project structure
    private val dbUrl = "https://chatapp-8dfe1-default-rtdb.asia-southeast1.firebasedatabase.app/"
    can initialize it like this -val dbRef = FirebaseDatabase.getReference(dbUrl).reference */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        //dbRef = FirebaseDatabase.getInstance().reference
        dbRef = FirebaseDatabase.getInstance().reference



        val name = intent.getStringExtra("name")
        val receiverUid = intent.getStringExtra("uid")
        val senderUid = firebaseAuth.currentUser!!.uid

        messageRoom = receiverUid+senderUid

        //Uploading Message to DB
        binding.btnSend.setOnClickListener {
            val message = binding.etMessage.text.toString()
            val messageObj = Message(message,senderUid)

            dbRef.child("chats").child(messageRoom!!).child("messages").push()
                .setValue(messageObj)
                .addOnSuccessListener {
                    Log.d("BRB","Message is uploaded to DB")
                }
                .addOnFailureListener {
                    Log.d("BRB","error uploading message $it")
                }

            binding.etMessage.text.clear()
        }

        val adapter = ChatScreenAdapter(this,messageList)
        binding.scChatScreen.adapter = adapter

    }

}