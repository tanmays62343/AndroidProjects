package com.trx.activities

import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import android.os.Handler
import android.os.Looper
import com.trx.R
import com.trx.database.StampsEntity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class StampsService : Service() {

    companion object {
        const val SERVICE_NOTIFICATION_ID = 1
    }

    private val handler = Handler(Looper.getMainLooper())
    private val delayMillis = 3 * 1000 // 10 seconds in milliseconds

    override fun onCreate() {
        super.onCreate()
        startForeground(SERVICE_NOTIFICATION_ID, createNotification())
        //scheduleNextWork()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val sdf = SimpleDateFormat("dd/MM/yyyy  - hh:mm:ss", Locale.getDefault())
        val currentTime = sdf.format(Date())

        GlobalScope.launch {
            MainActivity.database.connectStamps().insertStamp(StampsEntity(0, currentTime))
        }

        scheduleNextWork()

        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun createNotification() = NotificationCompat.Builder(this, "roomDemo_notification_channel")
        .setSmallIcon(R.drawable.ic_notification)
        .setContentTitle("Stamps Service")
        .setContentText("Running in the background")
        .setPriority(NotificationCompat.PRIORITY_LOW)
        .build()

    private fun scheduleNextWork() {
        handler.postDelayed({
            val serviceIntent = Intent(this, StampsService::class.java)
            startService(serviceIntent)
        }, delayMillis.toLong())
    }
}
