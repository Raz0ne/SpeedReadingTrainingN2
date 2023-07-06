package edu.application.ui.adapters

import android.content.SharedPreferences
import android.content.res.ColorStateList
import android.graphics.text.LineBreaker
import android.os.Build
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import edu.application.MyApplication.Companion.appContext
import edu.application.R

object TextFormatter {

    lateinit var sharedPreferences: SharedPreferences

    @JvmStatic
    fun setColor(textView: TextView) {
        textView.setBackgroundColor(sharedPreferences.getInt("lesson_background_color",
            ContextCompat.getColor(appContext, R.color.yellow_bg)))
        textView.setTextColor(sharedPreferences.getInt("lesson_text_color",
            ContextCompat.getColor(appContext, R.color.brown)))
    }

    @JvmStatic
    fun setBorder(cardView: MaterialCardView) {
        cardView.strokeColor = sharedPreferences.getInt("lesson_text_color",
            ContextCompat.getColor(appContext, R.color.brown))
    }

    @JvmStatic
    fun setBorder(button: MaterialButton) {
        button.strokeColor = ColorStateList.valueOf(sharedPreferences.getInt("lesson_text_color",
            ContextCompat.getColor(appContext, R.color.brown)))
    }

    @JvmStatic
    fun setTextSize(textView: TextView) {
        textView.textSize = sharedPreferences.getInt("lesson_text_size", 16).toFloat()
    }

    @JvmStatic
    fun setTextSettings(textView: TextView) {
        setColor(textView)
        setTextSize(textView)
        textView.setLineSpacing(0f,
            sharedPreferences.getFloat("lesson_text_line_spacing", 1.2f))
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
            textView.justificationMode = booleanToJustificationMode(
                sharedPreferences.getBoolean("lesson_text_justification", false))
    }

    @JvmStatic
    @RequiresApi(api = Build.VERSION_CODES.Q)
    fun booleanToJustificationMode(justification: Boolean): Int {
        return if (justification)
            LineBreaker.JUSTIFICATION_MODE_INTER_WORD else
                LineBreaker.JUSTIFICATION_MODE_NONE
    }
}