package com.trx

import androidx.lifecycle.ViewModel

class CounterViewModel : ViewModel() {

    var number = 0


    fun addOne() {
        number++
    }

}