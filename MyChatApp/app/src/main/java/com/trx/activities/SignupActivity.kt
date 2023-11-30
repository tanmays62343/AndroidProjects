package com.trx.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.trx.R
import com.trx.databinding.ActivitySignupBinding
import com.trx.models.User
import com.trx.utils.Consts.USERS_COLLECTION

class SignupActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySignupBinding

    private lateinit var firebaseAuth : FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        binding.btnSignup.setOnClickListener {
            signupUser()
        }

    }

    //A function to create a user in firebase
    private fun signupUser(){
        val name = binding.Name.text.toString()
        val email = binding.SEmail.text.toString()
        val password = binding.Password.text.toString()
        val confirmPassword = binding.ConfirmPassword.text.toString()

        if (email.isBlank() || password.isBlank()
            || confirmPassword.isBlank() || name.isBlank()
        ){
            Toast.makeText(this, "Fields Cannot Be Blank",
                Toast.LENGTH_SHORT).show()
            return
        }

        if(password != confirmPassword){
            Toast.makeText(this, "Passwords do not Match!",
                Toast.LENGTH_SHORT).show()
            return
        }

        firebaseAuth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(this){
                if (it.isSuccessful){
                    firebaseAuth.currentUser?.sendEmailVerification()
                        ?.addOnCompleteListener { emailTask ->
                            if(emailTask.isSuccessful){
                                Toast.makeText(this, "Verification Mail Sent",
                                    Toast.LENGTH_SHORT).show()
                                saveUserInfo()
                            }
                            else{
                                Toast.makeText(this, "Failed to sent verification mail",
                                    Toast.LENGTH_SHORT).show()
                            }
                        }

                    binding.Name.text.clear()
                    binding.SEmail.text.clear()
                    binding.Password.text.clear()
                    binding.ConfirmPassword.text.clear()

                } else{
                    Toast.makeText(this, "Error Creating User",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    //A function to save users info in firestore database
    private fun saveUserInfo(){
        firestore.collection(USERS_COLLECTION)
            .document(firebaseAuth.uid!!)
            .set(extractUser())
    }

    private fun extractUser(): User {
        val email = binding.SEmail.text.toString()
        val name = binding.Name.text.toString()
        return User(name, email)
    }
}