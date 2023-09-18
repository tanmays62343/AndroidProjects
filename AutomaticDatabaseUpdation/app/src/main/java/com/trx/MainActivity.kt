package com.trx

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.trx.databinding.ActivityMainBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Timer
import java.util.TimerTask

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    lateinit var database: StampsDatabase
    var allTime: LiveData<List<StampsEntity>>? = null
    val scope= MainScope()
    var job: Job?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        binding?.StampList?.layoutManager = LinearLayoutManager(this@MainActivity)


        database = Room.databaseBuilder(
            applicationContext,
            StampsDatabase::class.java,
            "StampsDB"
        ).build()

        callJob()

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

        fun stopUpdate(){
            job?.cancel()
            job=null
        }
}