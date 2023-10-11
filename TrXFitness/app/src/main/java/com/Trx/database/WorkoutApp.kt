package com.Trx.database

import android.app.Application
import com.Trx.database.HistoryDatabase

class WorkoutApp: Application() {

    val db by lazy {
        HistoryDatabase.getInstance(this)
    }

}