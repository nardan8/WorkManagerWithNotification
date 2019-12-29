package com.nariman.workmanagerwithnotification

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.work.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val constrains = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true)
            .build()

        val workRequest = OneTimeWorkRequest.Builder(ScheduleWork::class.java)
            .setConstraints(constrains)
            .setInitialDelay(10, TimeUnit.SECONDS)
            .build()

        startWorkButton.setOnClickListener {
            WorkManager.getInstance(applicationContext).enqueue(workRequest)
        }

        WorkManager.getInstance(applicationContext)
            .getWorkInfoByIdLiveData(workRequest.id)
            .observe(this, Observer {
            if (it.state == WorkInfo.State.SUCCEEDED){
                Log.i("mybackgroundwork", "Work succesfully finished, attempt: + ${it.runAttemptCount}")
                var str = "The sum of numbers from 0 to 100: "
                str += it.outputData.getInt("result", 0).toString()
                tvWorkResult.text = str
            }
            else if (it.state == WorkInfo.State.FAILED){
                Log.i("mybackgroundwork", "Work failed, attempt: ${it.runAttemptCount}")
            }
        })
    }
}
