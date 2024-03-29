package com.trx.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.trx.R
import com.trx.adapters.AddChatAdapter
import com.trx.databinding.ActivityAddChatBinding
import com.trx.models.User
import com.trx.utils.Const.USERS_COLLECTION

class AddChatActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAddChatBinding
    private lateinit var firestore: FirebaseFirestore
    private lateinit var firebaseAuth: FirebaseAuth

    private var usersList = mutableListOf<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firestore = FirebaseFirestore.getInstance()
        firebaseAuth = FirebaseAuth.getInstance()

        usersList.clear()
        firestore.collection(USERS_COLLECTION)
            .addSnapshotListener { value, error ->

                if(value == null || error != null){
                    Toast.makeText(this,
                        "Error in fetching data",
                        Toast.LENGTH_SHORT).show()
                    return@addSnapshotListener
                }

                //usersList.addAll(value.toObjects(User::class.java)) if we have to add all

                for(doc in value.documents){
                    val cUser = doc.toObject(User::class.java)
                    if(cUser != null && cUser.uniqueID != firebaseAuth.currentUser!!.uid){
                        usersList.add(cUser)
                    }
                }

                val adapter = AddChatAdapter(this,usersList)
                binding.recyclerNames.adapter = adapter
            }

    }
}