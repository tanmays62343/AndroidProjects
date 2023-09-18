package com.trx

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface StampsDao {

    @Insert
    suspend fun insertStamp(stampsEntity : StampsEntity)

    @Query("SELECT * FROM `Stamps-Entry`")
    fun getStamps() : LiveData<List<StampsEntity>>

}