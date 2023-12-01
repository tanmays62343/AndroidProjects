package com.trx.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.trx.databinding.ActivitySignupBinding
import com.trx.models.User
import com.trx.utils.Const.USERS_COLLECTION

class SignupActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySignupBinding

    //for firebase authentication
    private lateinit var firebaseAuth: FirebaseAuth

    //for firebase firestore database
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        binding.SignupBtn.setOnClickListener {
            signUpUser()
        }

        binding.tvLogin.setOnClickListener {
            Intent(this,LoginActivity::class.java).also {
                startActivity(it)
            }
            finish()
        }

    }

    //creating a user using firebase
    private fun signUpUser() {
        val name = binding.etSname.text.toString()
        val email = binding.etSemail.text.toString()
        val password = binding.etSpassword.text.toString()
        val confirmPassword = binding.etCnfrmPass.text.toString()

        //General ID and Password Authentication
        if (email.isBlank() || password.isBlank()
            || confirmPassword.isBlank() || name.isBlank()
        ) {
            Toast.makeText(
                this, "Fields cannot be Blank",
                Toast.LENGTH_SHORT
            ).show()
            return
        }
        if (password != confirmPassword) {
            Toast.makeText(
                this, "Passwords do not Match",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        //firebase function for creating a user
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    val user = firebaseAuth.currentUser
                    val mUser = User(name,email,user!!.uid)
                    user.sendEmailVerification()
                        .addOnCompleteListener { emailTask ->
                            if (emailTask.isSuccessful) {
                                Toast.makeText(
                                    this, "Verification email sent",
                                    Toast.LENGTH_LONG
                                ).show()

                                saveUserInfo(user.uid,mUser)

                                binding.etSemail.text?.clear()
                                binding.etSpassword.text?.clear()
                                binding.etCnfrmPass.text?.clear()
                                binding.etSname.text?.clear()
                                startActivity(Intent(this, LoginActivity::class.java))
                                finish()
                                //firebaseAuth.signOut()
                            } else {
                                Toast.makeText(
                                    this,
                                    "Failed to sent Verification email, Check Your Email ID",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                } else {
                    Toast.makeText(
                        this,
                        "Error Creating User, Email Already Exist",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }

    private fun saveUserInfo(userUid: String, mUser: User) {
        firestore.collection(USERS_COLLECTION)
            .document(userUid)
            .set(mUser)
    }


}