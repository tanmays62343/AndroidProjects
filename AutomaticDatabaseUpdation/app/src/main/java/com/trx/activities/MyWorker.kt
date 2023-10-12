package com.trx.activities

import android.annotation.SuppressLint
import android.content.Context
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.trx.activities.MainActivity.Companion.database
import com.trx.database.StampsEntity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.concurrent.TimeUnit

class MyWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {

    // This method is executed on a background thread, Your background work goes here
    @SuppressLint("SimpleDateFormat")
    override fun doWork(): Result {
        /*try {
            //Updating the Database
            val sdf = SimpleDateFormat("dd/MM/yyyy  - hh:mm:ss")
            val currentTime = sdf.format(Date())
            database.connectStamps().insertStamp(StampsEntity(0, currentTime))

            // Reschedule the worker to run again in 10 minutes
            val nextRunRequest = OneTimeWorkRequest.Builder(MyWorker::class.java)
                .setInitialDelay(10, TimeUnit.SECONDS)
                .build()
            WorkManager.getInstance(applicationContext).enqueue(nextRunRequest)

        } catch (e: Exception) {
            return Result.failure()
        }*/
        return Result.success()
    }

    /*companion object {
        fun enqueue() {
            val periodicWorkRequest = PeriodicWorkRequestBuilder<MyWorker>(
                20,TimeUnit.MINUTES
            ).build()
            val oneTimeWorkRequest = OneTimeWorkRequestBuilder<MyWorker>()
                .setInitialDelay(10,TimeUnit.SECONDS)
                .build()
            WorkManager.getInstance().enqueue(oneTimeWorkRequest)
        }
    }*/
}
