package com.trx.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.trx.adapters.ChatScreenAdapter
import com.trx.databinding.ActivityChatScreenBinding
import com.trx.models.Message

class ChatScreenActivity : AppCompatActivity() {


    private lateinit var binding : ActivityChatScreenBinding

    private var messageList = mutableListOf<Message>()

    private var sendersRoom : String? = null        //Its simple Unique room for current Sender
    private var receiversRoom : String? = null      //Think when the receiver Logins

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

        sendersRoom = receiverUid+senderUid
        receiversRoom = senderUid+receiverUid

        val adapter = ChatScreenAdapter(this@ChatScreenActivity,messageList)
        binding.scChatScreen.adapter = adapter

        //Logic to show data in the chat screen
        dbRef.child("chats").child(sendersRoom!!).child("messages")
            .addValueEventListener(object : ValueEventListener{
                @SuppressLint("NotifyDataSetChanged")
                override fun onDataChange(snapshot: DataSnapshot) {

                    messageList.clear()

                    for(i in snapshot.children){
                        val message = i.getValue(Message::class.java)
                        messageList.add(message!!)
                    }
                    adapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    //TODO : (Handel Error)
                }

            })

        //Uploading Messages to DB
        binding.btnSend.setOnClickListener {
            val message = binding.etMessage.text.toString()
            val messageObj = Message(message,senderUid)

            //Uploading Messages in sender and receiver room
            dbRef.child("chats").child(sendersRoom!!).child("messages").push()
                .setValue(messageObj)
                .addOnSuccessListener {
                    dbRef.child("chats").child(receiversRoom!!).child("messages").push()
                        .setValue(messageObj)
                    Log.d("BRB","Message gone in receivers Room")
                }
                .addOnFailureListener {
                    Log.d("BRB","error uploading message $it")
                }
            binding.etMessage.text.clear()
        }



    }

}