package edu.application.ui.listeners;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

public class AuthTextWatcher implements TextWatcher {

    private View managed;

    public AuthTextWatcher(View view) {
        managed = view;
    }
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (charSequence.length() != 0)
            managed.setVisibility(View.VISIBLE);
        else
            managed.setVisibility(View.INVISIBLE);
    }

    @Override
    public void afterTextChanged(Editable editable) {}
}
