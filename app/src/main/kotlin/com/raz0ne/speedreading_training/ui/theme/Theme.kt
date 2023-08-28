package com.raz0ne.speedreading_training.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val lightThemeColors = lightColors(
    primary = Yellow,
    onPrimary = Brown
)

private val darkThemeColors = /*darkColors()*/ lightThemeColors

@Composable
fun AppTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = if (useDarkTheme) darkThemeColors else lightThemeColors,
        typography = typography,
        shapes = shapes,
        content = content
    )
}