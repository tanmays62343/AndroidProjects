package com.trx.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.trx.adapters.RecyclerAdapters
import com.trx.database.StampsDatabase
import com.trx.database.StampsEntity
import com.trx.databinding.ActivityMainBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    private lateinit var database: StampsDatabase
    private var allTime: LiveData<List<StampsEntity>>? = null
    private val scope= MainScope()
    private var job: Job?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        binding?.StampList?.layoutManager = LinearLayoutManager(this@MainActivity)

        //initializing database
        database = Room.databaseBuilder(
            applicationContext,
            StampsDatabase::class.java,
            "StampsDB"
        ).build()

        callJob()

        //giving the list of time stamps to recycler view
        allTime = database.connectStamps().getStamps()
        allTime!!.observe(this@MainActivity) {
            if (!it.isNullOrEmpty()) {
                binding?.StampList?.adapter = RecyclerAdapters(it)
            }
        }
    }

        private fun callJob(){
            stopUpdate()
            job=scope.launch {
                while (true){
                    getData()
                    delay(600000)
                }
            }
        }

        @SuppressLint("SimpleDateFormat")
        suspend fun getData(){
            val sdf = SimpleDateFormat("dd/MM/yyyy  - hh:mm:ss")
            val currentTime = sdf.format(Date())
            database.connectStamps().insertStamp(StampsEntity(0, currentTime))

        }

        private fun stopUpdate(){
            job?.cancel()
            job=null
        }
}