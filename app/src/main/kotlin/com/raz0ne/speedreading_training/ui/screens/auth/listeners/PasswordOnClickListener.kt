package com.raz0ne.speedreading_training.ui.screens.auth.listeners

import android.text.InputType
import android.view.View
import android.widget.EditText
import com.raz0ne.speedreading_training.R

class PasswordOnClickListener(private val et: EditText) : View.OnClickListener {

    override fun onClick(view: View) {
        if (et.inputType == InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD) {
            view.setBackgroundResource(R.drawable.eye_opened)
            et.inputType = InputType.TYPE_CLASS_TEXT
        }
        else {
            view.setBackgroundResource(R.drawable.eye_closed)
            et.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        }
    }
}