package edu.application.ui.listeners

import android.text.Editable
import android.text.TextWatcher
import android.view.View

class AuthTextWatcher(private val managed: View) : TextWatcher {

    override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

    override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
        if (charSequence.isNotEmpty())
            managed.visibility = View.VISIBLE
        else
            managed.visibility = View.INVISIBLE
    }

    override fun afterTextChanged(editable: Editable) {}
}