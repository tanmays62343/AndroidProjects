package com.trx

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [StampsEntity::class], version = 1)
abstract class StampsDatabase : RoomDatabase() {

    abstract fun connectStamps() : StampsDao

}