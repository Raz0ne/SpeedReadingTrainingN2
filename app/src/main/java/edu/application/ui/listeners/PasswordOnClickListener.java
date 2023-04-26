package edu.application.ui.listeners;

import android.text.InputType;
import android.view.View;
import android.widget.EditText;

import edu.application.R;

public class PasswordOnClickListener implements View.OnClickListener {

    private EditText et;

    public PasswordOnClickListener(EditText password_et) {
        et = password_et;
    }

    @Override
    public void onClick(View view) {
        if (et.getInputType() ==
                (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
            view.setBackgroundResource(R.drawable.eye_opened);
            et.setInputType(InputType.TYPE_CLASS_TEXT);
        }
        else {
            view.setBackgroundResource(R.drawable.eye_closed);
            et.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
    }
}
