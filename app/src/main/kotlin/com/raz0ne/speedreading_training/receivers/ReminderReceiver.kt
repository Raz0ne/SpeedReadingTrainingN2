package com.raz0ne.speedreading_training.receivers

import android.Manifest
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.raz0ne.speedreading_training.R
import com.raz0ne.speedreading_training.extension.setLocale
import com.raz0ne.speedreading_training.ui.activities.MainActivity
import com.raz0ne.speedreading_training.util.schedulePushNotifications

class ReminderReceiver : BroadcastReceiver() {

    companion object {
        const val ACTION = "com.application.ACTION_REMINDER_NOTIFICATION"
        const val NOTIFICATION_ID = 101
    }

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == ACTION) {
            showPushNotification(ContextWrapper(context.setLocale()))

            schedulePushNotifications(context)
        }
    }

    private fun showPushNotification(context: Context) {
        Log.e("razon", "notification")

        val intent = Intent(context, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        val pendingIntent = PendingIntent.getActivity(context, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        val builder = NotificationCompat.Builder(context,
            context.getString(R.string.notification_channel_reminders_id))
            .setSmallIcon(R.drawable.outline_timer_24)
            .setLargeIcon(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.resources,
                R.drawable.icon), 128, 128, false))
            .setContentTitle(context.getString(R.string.reminder_notification_title))
            .setContentText(context.getString(R.string.reminder_notification_text))
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setContentIntent(pendingIntent)

        with(NotificationManagerCompat.from(context)) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS)
                == PackageManager.PERMISSION_GRANTED)
                notify(NOTIFICATION_ID, builder.build())
        }
    }
}