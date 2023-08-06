package com.application.ui.fragments.navigation.account.settings

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import android.text.format.DateFormat.is24HourFormat
import com.application.ui.fragments.navigation.adapters.TextFormatter
import java.util.Calendar

class ReminderTimePickerFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val hour = TextFormatter.sharedPreferences.getInt("ReminderHour",
            calendar.get(Calendar.HOUR))
        val minute = TextFormatter.sharedPreferences.getInt("ReminderMinute",
            calendar.get(Calendar.MINUTE))

        val isEnabled = TextFormatter.sharedPreferences.getBoolean("ReminderEnable", false)

        return ReminderTimePickerDialog(requireContext(), isEnabled, hour, minute,
            is24HourFormat(requireContext()))
    }
}