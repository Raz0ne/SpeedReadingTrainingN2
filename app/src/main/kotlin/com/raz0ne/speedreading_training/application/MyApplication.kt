package com.raz0ne.speedreading_training.application

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import com.raz0ne.speedreading_training.R
import com.google.firebase.FirebaseApp

class MyApplication : Application() {

    companion object {
        lateinit var appContext: Context
            private set
            @JvmStatic get
    }

    override fun onCreate() {
        super.onCreate()

        appContext = applicationContext

        createNotificationChannels()

        FirebaseApp.initializeApp(this)
    }

    private fun createNotificationChannels() {
        val reminderNotificationChannel = NotificationChannel(
            getString(R.string.notification_channel_reminders_id),
            getString(R.string.notification_channel_reminders_name),
            NotificationManager.IMPORTANCE_HIGH
        )

        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(reminderNotificationChannel)
    }
}