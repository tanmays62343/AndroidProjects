package com.trx

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel

class CounterViewModel(app : Application) : AndroidViewModel(app) {

    var number = 0

    fun addOne() {
        number++
    }

}