package com.trx.dagger2

import android.util.Log

class UserRegistrationService {

    private val userRepository = UserRepository()
    private val emailService = EmailService()

    fun RegisterUser(email : String, password : String){
        userRepository.saveUser(email,password)
        emailService.send(email,"no-reply@trx.com")
    }

}

/*
* Unit Testing
* Single Responsibility
* Lifetime of these Objects
* Extensible
* Remember example of class car(engine)
*/