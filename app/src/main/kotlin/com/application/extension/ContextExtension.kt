package com.application.extension

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

fun Context.setLocale() : Context {
    var lang = getSharedPreferences("Settings", AppCompatActivity.MODE_PRIVATE).getString("language", "sys")!!
    if (lang == "sys")
        lang = Locale.getDefault().language
    val locale = Locale(lang)

    val config = resources.configuration
    config.setLocale(locale)
    config.setLayoutDirection(locale)

    return createConfigurationContext(config)
}