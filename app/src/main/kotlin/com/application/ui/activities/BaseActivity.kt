package com.application.ui.activities

import android.content.Context
import android.content.ContextWrapper
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

open class BaseActivity : AppCompatActivity() {

    private fun Context.setLocale() : Context {
        var lang = getSharedPreferences("Settings", MODE_PRIVATE).getString("language", "sys")!!
        if (lang == "sys")
            lang = Locale.getDefault().language
        val locale = Locale(lang)

        val config = resources.configuration
        config.setLocale(locale)
        config.setLayoutDirection(locale)

        return createConfigurationContext(config)
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(ContextWrapper(newBase.setLocale()))
    }
}