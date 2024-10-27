package com.example.numberguesser.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun ScaffoldNavigationApp() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "Main"
    ) {
        composable(route = "Main") {
            MainsScreen(navController)
        }
        composable("Game") {
            GameScreen(navController)
        }
        composable("Info") {
            InfoScreen(navController)
        }
    }
}
