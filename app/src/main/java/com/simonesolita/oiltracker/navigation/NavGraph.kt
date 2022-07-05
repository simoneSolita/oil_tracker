package com.simonesolita.oiltracker.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.simonesolita.oiltracker.ui.graph.GraphScreen
import com.simonesolita.oiltracker.ui.main.MainActivityViewModel
import com.simonesolita.oiltracker.ui.main.SplashScreen

@Composable
fun setupNavGraph(
    navController: NavHostController,
    viewModel: ViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(
            route = Screen.Splash.route
        ) {
            SplashScreen(
                navController = navController,
                viewModel = viewModel as MainActivityViewModel
            )
        }
        composable(
            route = Screen.Graph.route
        ) {
            GraphScreen(
                navController = navController,
            )
        }
    }
}