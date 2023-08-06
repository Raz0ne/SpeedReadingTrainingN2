package com.application.ui.fragments.navigation.account.settings

import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import com.application.databinding.DialogReminderTimePickerBinding
import com.application.ui.fragments.navigation.adapters.TextFormatter

class ReminderTimePickerDialog(context: Context, private val isChecked: Boolean,
                               private val hour: Int, private val minute: Int,
                               private val is24HourFormat: Boolean) : Dialog(context) {

    private lateinit var binding: DialogReminderTimePickerBinding
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DialogReminderTimePickerBinding.inflate(layoutInflater)
        editor = TextFormatter.sharedPreferences.edit()
        setContentView(binding.root)

        binding.cancel.setOnClickListener { cancel() }
        binding.done.setOnClickListener { save() }

        binding.enableSwitch.isChecked = isChecked

        binding.timePicker.apply {
            hour = this@ReminderTimePickerDialog.hour
            minute = this@ReminderTimePickerDialog.minute
            setIs24HourView(is24HourFormat)
        }
    }

    private fun save() {
        editor.putBoolean("ReminderEnable", binding.enableSwitch.isChecked).apply()
        editor.putInt("ReminderHour", binding.timePicker.hour)
            .putInt("ReminderMinute", binding.timePicker.minute).apply()

        dismiss()
    }
}