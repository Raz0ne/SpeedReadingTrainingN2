package com.raz0ne.speedreading_training.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.util.Log
import com.raz0ne.speedreading_training.util.schedulePushNotifications

class BootReceiver : BroadcastReceiver() {

    @Override
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            Log.e("razon", "reboot")
            if (context.getSharedPreferences("Settings", MODE_PRIVATE)
                    .getBoolean("ReminderEnable", false)
            )
                schedulePushNotifications(context)
        }
    }
}