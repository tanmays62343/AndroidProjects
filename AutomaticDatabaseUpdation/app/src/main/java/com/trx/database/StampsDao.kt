package com.trx.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.trx.database.StampsEntity

@Dao
interface StampsDao {

    @Insert
    suspend fun insertStamp(stampsEntity : StampsEntity)

    @Query("SELECT * FROM `Stamps-Entry`")
    fun getStamps() : LiveData<List<StampsEntity>>

}