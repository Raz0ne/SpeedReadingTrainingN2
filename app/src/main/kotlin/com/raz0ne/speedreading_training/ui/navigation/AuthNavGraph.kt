package com.raz0ne.speedreading_training.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation

fun NavGraphBuilder.authGraph(navController: NavController) {
    navigation(startDestination = NavigationItem.SignIn.route, route = "auth") {
        composable(route = NavigationItem.SignIn.route) {  }
        composable(route = NavigationItem.SignUp.route) {  }
    }
}