package com.raz0ne.speedreading_training.ui.fragments.auth.listeners

import android.text.Editable
import android.text.TextWatcher
import android.view.View

class AuthTextWatcher(private val managed: View) : TextWatcher {

    override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

    override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
        if (charSequence.isEmpty())
            managed.visibility = View.INVISIBLE
        else
            managed.visibility = View.VISIBLE
    }

    override fun afterTextChanged(editable: Editable) {}
}