package com.raz0ne.speedreading_training.ui.navigation

import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.raz0ne.speedreading_training.R
import com.raz0ne.speedreading_training.ui.theme.Brown
import com.raz0ne.speedreading_training.ui.theme.Gold

@Composable
fun TopBar() {
    TopAppBar(
        title = { Text(text = stringResource(R.string.app_name), fontSize = 18.sp) },
        backgroundColor = Gold,
        contentColor = Brown
    )
}