package com.nariman.workmanagerwithnotification

import android.annotation.TargetApi
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat

class NotificationUtils(context: Context): ContextWrapper(context){

    val CHANNEL_ID = "myChannel"
    val CHANNEL_NAME = "myChannelName"

    private var manager: NotificationManager

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            createChannel()
        }
        manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    @TargetApi(Build.VERSION_CODES.O)
    private fun createChannel() {
        val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
        getManager()
    }

    fun getManager() = manager

    fun getNotification(context: Context): Notification {

        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)

        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle("My Work Notification")
            .setContentInfo("Hello!")
            .setContentText("Lorem ipsum dolor sit amet, consectetur adipiscing elit")
            .setSmallIcon(R.drawable.notification)
            .setContentIntent(pendingIntent)
            .setStyle(NotificationCompat
                .BigPictureStyle()
                .bigPicture(
                    BitmapFactory.
                        decodeResource(applicationContext.resources, R.drawable.notification_picture
                        )
                )
            )
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .setAutoCancel(true)
            .build()
    }
}