package com.raz0ne.speedreading_training.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = BottomNavItem.Training.route) {
        composable(BottomNavItem.Training.route) {
            //TrainingScreen()
        }
        composable(BottomNavItem.Account.route) {
            //AccountScreen()
        }

        authGraph(navController)
    }
}