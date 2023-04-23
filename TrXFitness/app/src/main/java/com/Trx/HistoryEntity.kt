package com.Trx

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "History-Table")
data class HistoryEntity(
    @PrimaryKey
    val Date : String
)
