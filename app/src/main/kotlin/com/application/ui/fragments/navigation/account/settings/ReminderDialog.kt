package com.application.ui.fragments.navigation.account.settings

import android.app.AlertDialog
import android.content.ComponentName
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.format.DateFormat
import com.application.R
import com.application.databinding.DialogReminderBinding
import com.application.receivers.BootReceiver
import com.application.receivers.ReminderReceiver
import com.application.ui.fragments.navigation.adapters.TextFormatter.sharedPreferences
import com.application.util.cancelAlarm
import com.application.util.schedulePushNotifications
import java.util.Calendar

class ReminderDialog(context: Context) : AlertDialog(context) {

    private lateinit var binding: DialogReminderBinding
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DialogReminderBinding.inflate(layoutInflater)
        editor = sharedPreferences.edit()

        setView(binding.root)
        setTitle(R.string.settings_reminder_btn_txt)

        binding.cancel.setOnClickListener { dismiss() }
        binding.done.setOnClickListener { save() }

        val isChecked = sharedPreferences.getBoolean("ReminderEnable", false)

        binding.modeSwitch.isChecked = isChecked

        val calendar = Calendar.getInstance()
        val hour = sharedPreferences.getInt("ReminderHour", calendar.get(Calendar.HOUR_OF_DAY))
        val minute = sharedPreferences.getInt("ReminderMinute", calendar.get(Calendar.MINUTE))

        binding.timePicker.apply {
            this.hour = hour
            this.minute = minute
            setIs24HourView(DateFormat.is24HourFormat(context))
        }

        super.onCreate(savedInstanceState)
    }

    private fun save() {
        editor.putBoolean("ReminderEnable", binding.modeSwitch.isChecked)
            .putInt("ReminderHour", binding.timePicker.hour)
            .putInt("ReminderMinute", binding.timePicker.minute).apply()

        if (binding.modeSwitch.isChecked)
            schedulePushNotifications(context)
        else
            cancelAlarm(context)

        dismiss()
    }
}