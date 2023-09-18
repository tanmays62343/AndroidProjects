package com.trx

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "Stamps-Entry")
data class StampsEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Long,

    @ColumnInfo(name = "time_Stamps")
    val tStamp : String
)
