package com.nariman.workmanagerwithnotification

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import androidx.work.workDataOf

class ScheduleWork(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {

        var sumOfNumbers = 0

        Log.i("mybackgroundwork","Start.CurrentThread: ${Thread.currentThread().name}\n")
        try {
            //val myData = workDataOf("result".to(sumOfNumbers))
            val data = Data.Builder().putInt("result", sumOfNumbers).build()

            val notifUtils = NotificationUtils(applicationContext)
            val manager = notifUtils.getManager()
            val notification = notifUtils.getNotification(applicationContext)
            manager.notify(2, notification)

            return Result.success(data)
        }catch (e: Exception){
            return Result.failure()
        }
    }
}