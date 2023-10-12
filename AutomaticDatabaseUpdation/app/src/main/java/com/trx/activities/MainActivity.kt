package com.trx.activities


import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.trx.adapters.RecyclerAdapters
import com.trx.database.StampsDatabase
import com.trx.database.StampsEntity
import com.trx.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

    companion object {
        lateinit var database: StampsDatabase
    }

    private var binding: ActivityMainBinding? = null
    private var allTime: LiveData<List<StampsEntity>>? = null
    //private val scope= MainScope()
    //private var job: Job?=null

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

        //MyWorker.enqueue()
        createNotificationChannel()

        val serviceIntent = Intent(this, StampsService::class.java)
        startService(serviceIntent)

        /*val initialRequest = OneTimeWorkRequest.Builder(MyWorker::class.java)
            .setInitialDelay(10, TimeUnit.SECONDS)
            .build()
        WorkManager.getInstance(applicationContext).enqueue(initialRequest)*/

        updateRecyclerView()
        //callJob()
    }

    //giving the list of time stamps to recycler view
    fun updateRecyclerView() {
        allTime = database.connectStamps().getStamps()
        allTime!!.observe(this@MainActivity) {
            if (!it.isNullOrEmpty()) {
                binding?.StampList?.adapter = RecyclerAdapters(it)
            }
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "roomDemo_notification_channel"
            val channelName = "roomDemo_notification"
            val notificationManager = getSystemService(NotificationManager::class.java)
            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_LOW)
            notificationManager.createNotificationChannel(channel)
        }
    }


    /*private fun callJob(){
        stopUpdate()
        job=scope.launch {
            while (true){
                getData()
                delay(600000)
            }
        }
    }*/

    /*@SuppressLint("SimpleDateFormat")
    fun putData(){
        val sdf = SimpleDateFormat("dd/MM/yyyy  - hh:mm:ss")
        val currentTime = sdf.format(Date())
        database.connectStamps().insertStamp(StampsEntity(0, currentTime))

        updateRecyclerView()
    }*/

    /*private fun stopUpdate(){
        job?.cancel()
        job=null
    }*/
}