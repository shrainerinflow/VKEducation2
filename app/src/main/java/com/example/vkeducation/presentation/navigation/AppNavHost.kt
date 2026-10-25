package com.example.vkeducation.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.vkeducation.presentation.details.AppDetailsScreen
import com.example.vkeducation.presentation.list.AppListScreen

private const val ROUTE_LIST = "list"
private const val ARG_APP_ID = "appId"
private const val ROUTE_DETAILS = "details/{$ARG_APP_ID}"

@Composable
fun AppNavHost(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = ROUTE_LIST) {

        composable(ROUTE_LIST) {
            AppListScreen(
                onAppClick = { app -> navController.navigate("details/${app.id}") },
            )
        }

        composable(
            route = ROUTE_DETAILS,
            arguments = listOf(navArgument(ARG_APP_ID) { type = NavType.IntType }),
        ) { entry ->
            val appId = entry.arguments?.getInt(ARG_APP_ID) ?: return@composable
            AppDetailsScreen(
                appId = appId,
                onBackClick = { navController.popBackStack() },
                // клик по приложению из «Витрины» → список приложений
                onShowcaseAppClick = { navController.popBackStack(ROUTE_LIST, inclusive = false) },
            )
        }
    }
}
