package com.trx.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.trx.R
import com.trx.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding

    private lateinit var firebaseAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        val currentUser = firebaseAuth.currentUser

        //If the User Exists and is verified it will redirect it to main activity
        if(currentUser != null && currentUser.isEmailVerified){
            Intent(this,MainActivity::class.java).also {
                startActivity(it)
            }
        }

        binding.loginButton.setOnClickListener {
            login()
        }

        binding.tvSignup.setOnClickListener {
            Intent(this,SignupActivity::class.java).also {
                startActivity(it)
            }
            finish()
        }

    }

    //Here we are authenticating the user
    private fun login(){
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()

        if(email.isBlank() || password.isBlank()){
            Toast.makeText(this, "Fields cannot be Blank",
                Toast.LENGTH_SHORT).show()
            return
        }

        //Login function of firebase
        firebaseAuth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener(this){
                if(it.isSuccessful){
                    val user = firebaseAuth.currentUser
                    if(user!!.isEmailVerified) {
                        Toast.makeText(this, "Login Successful",
                            Toast.LENGTH_SHORT).show()
                        intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }else{
                        Toast.makeText(this, "Please Verify your email",
                            Toast.LENGTH_SHORT).show()
                    }
                }
                else{
                    Toast.makeText(this, "Authentication Failed",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

}