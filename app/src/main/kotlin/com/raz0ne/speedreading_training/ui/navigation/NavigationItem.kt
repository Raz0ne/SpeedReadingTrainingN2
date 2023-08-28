package com.raz0ne.speedreading_training.ui.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.raz0ne.speedreading_training.R

sealed class NavigationItem(val route: String) {
    data object SignIn : NavigationItem("signIn")

    data object SignUp : NavigationItem("signUp")
}

sealed class BottomNavItem(
    route: String,
    @StringRes val titleResId: Int,
    @DrawableRes val iconResId: Int,
) : NavigationItem(route) {

    data object Training : BottomNavItem(
        route = "training",
        titleResId = R.string.menu_training,
        iconResId = R.drawable.round_menu_book_24
    )

    data object Account : BottomNavItem(
        route = "account",
        titleResId = R.string.menu_account,
        iconResId = R.drawable.outline_account_circle_24
    )
}