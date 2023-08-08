package com.application.ui.fragments.navigation.account.settings

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.text.format.DateFormat
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContentProviderCompat.requireContext
import com.application.R
import com.application.databinding.DialogReminderTimePickerBinding
import com.application.ui.fragments.navigation.adapters.TextFormatter.sharedPreferences
import java.util.Calendar

class ReminderTimePickerDialog(context: Context) : AlertDialog(context) {

    private lateinit var binding: DialogReminderTimePickerBinding
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DialogReminderTimePickerBinding.inflate(layoutInflater)
        editor = sharedPreferences.edit()

        setView(binding.root)
        setTitle(R.string.settings_reminder_btn_txt)

        binding.cancel.setOnClickListener { dismiss() }
        binding.done.setOnClickListener { save() }

        val isChecked = sharedPreferences.getBoolean("ReminderEnable", false)

        binding.modeSwitch.isChecked = isChecked

        val calendar = Calendar.getInstance()
        val hour = sharedPreferences.getInt("ReminderHour", calendar.get(Calendar.HOUR))
        val minute = sharedPreferences.getInt("ReminderMinute", calendar.get(Calendar.MINUTE))

        binding.timePicker.apply {
            this.hour = hour
            this.minute = minute
            setIs24HourView(DateFormat.is24HourFormat(context))
        }

        super.onCreate(savedInstanceState)
    }

    private fun save() {
        editor.putBoolean("ReminderEnable", binding.modeSwitch.isChecked).apply()
        editor.putInt("ReminderHour", binding.timePicker.hour)
            .putInt("ReminderMinute", binding.timePicker.minute).apply()

        dismiss()
    }
}