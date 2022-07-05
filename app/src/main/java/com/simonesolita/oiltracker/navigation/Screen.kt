package com.simonesolita.oiltracker.navigation

sealed class Screen(val route : String) {
    object Splash : Screen(route = "splash_screen")
    object Graph : Screen(route = "graph_screen")
}
