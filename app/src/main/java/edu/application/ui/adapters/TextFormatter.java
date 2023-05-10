package edu.application.ui.adapters;

import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.text.LineBreaker;
import android.os.Build;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

import edu.application.MyApplication;
import edu.application.R;

public final class TextFormatter {

    public static SharedPreferences sharedPreferences;

    public static void setSharedPreferences(SharedPreferences sharedPreferences) {
        TextFormatter.sharedPreferences = sharedPreferences;
    }

    public static void setColor(TextView textView) {
        textView.setBackgroundColor(sharedPreferences.getInt("lesson_background_color",
                ContextCompat.getColor(MyApplication.getAppContext(), R.color.yellow_bg)));
        textView.setTextColor(sharedPreferences.getInt("lesson_text_color",
                ContextCompat.getColor(MyApplication.getAppContext(), R.color.brown)));
    }

    public static void setBorder(MaterialCardView cardView) {
        cardView.setStrokeColor(ColorStateList.valueOf(
                sharedPreferences.getInt("lesson_text_color",
                        ContextCompat.getColor(MyApplication.getAppContext(), R.color.brown))));
    }

    public static void setBorder(MaterialButton button) {
        button.setStrokeColor(ColorStateList.valueOf(
                sharedPreferences.getInt("lesson_text_color",
                        ContextCompat.getColor(MyApplication.getAppContext(), R.color.brown))));
    }

    public static void setTextSize(TextView textView) {
        textView.setTextSize(sharedPreferences.getInt("lesson_text_size", 16));
    }

    public static void setTextSettings(TextView textView) {
        setColor(textView);
        setTextSize(textView);
        textView.setLineSpacing(0,
                sharedPreferences.getFloat("lesson_text_line_spacing", 1.2F));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
            textView.setJustificationMode(booleanToJustificationMode(
                    sharedPreferences.getBoolean("lesson_text_justification", false)));
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    public static int booleanToJustificationMode(boolean justification) {
        if (justification)
            return LineBreaker.JUSTIFICATION_MODE_INTER_WORD;
        return LineBreaker.JUSTIFICATION_MODE_NONE;
    }
}
