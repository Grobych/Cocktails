package com.globa.cocktails.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun NavController() {
    val navController = rememberNavController()

    val navigateToCocktail: (String) -> Unit = { cocktailId ->
        navController.navigate("cocktailInfo/${cocktailId}")
    }

    NavHost(navController = navController, startDestination = "cocktailList") {
        composable(
            route = "cocktailList"
        ) {
            CocktailListScreen(onItemClickAction = navigateToCocktail)
        }
        composable(
            route = "cocktailInfo/{cocktailId}",
            arguments = listOf(navArgument("cocktailId") { type = NavType.StringType })
        ) {
            CocktailInfoScreen()
        }
    }
}