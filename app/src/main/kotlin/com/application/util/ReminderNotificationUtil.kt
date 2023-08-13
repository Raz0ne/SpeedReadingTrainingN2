package com.application.util

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.application.receivers.ReminderReceiver
import java.util.Calendar

fun schedulePushNotifications(context: Context) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, ReminderReceiver::class.java)
    intent.action = ReminderReceiver.ACTION
    val alarmPendingIntent = PendingIntent.getBroadcast(context, 0, intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

    var hour: Int
    var minute: Int
    with(context.getSharedPreferences("Settings", Context.MODE_PRIVATE)) {
        hour = getInt("ReminderHour", 0)
        minute = getInt("ReminderMinute", 0)
    }
    alarmManager.cancel(alarmPendingIntent)

    val calendar = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, hour)
        set(Calendar.MINUTE, minute)
        set(Calendar.SECOND, 0)

        val now = Calendar.getInstance()
        if (before(now) or equals(now))
            add(Calendar.DATE, 1)
    }

    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.timeInMillis,
        alarmPendingIntent)

    Log.e("razon", "notification scheduled: $hour:$minute")
}

fun cancelAlarm(context: Context) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, ReminderReceiver::class.java)
    intent.action = ReminderReceiver.ACTION
    val alarmPendingIntent = PendingIntent.getBroadcast(context, 0, intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

    alarmManager.cancel(alarmPendingIntent)
}