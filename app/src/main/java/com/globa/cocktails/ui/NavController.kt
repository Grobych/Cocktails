package com.globa.cocktails.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.globa.cocktails.feature.details.api.CocktailInfoScreen
import com.globa.cocktails.feature.edit.api.CocktailRedactorScreen
import com.globa.cocktails.ui.cocktaillist.CocktailListScreen

@Composable
fun NavController() {
    val navController = rememberNavController()

    val navigateToCocktail: (Int) -> Unit = { cocktailId ->
        navController.navigate("cocktailInfo?cocktailId=${cocktailId}")
    }
    val navigateToRedactor: (Int, String) -> Unit = { cocktailId, mode ->
        navController.navigate("cocktailRedactor?cocktailId=${cocktailId}&mode=${mode}")
    }
    val navigateToBack: () -> Unit = {
        navController.popBackStack()
    }

    NavHost(navController = navController, startDestination = "cocktailList") {
        composable(
            route = "cocktailList"
        ) {
            CocktailListScreen(onItemClickAction = navigateToCocktail)
        }
        composable(
            route = "cocktailInfo?cocktailId={cocktailId}",
            arguments = listOf(navArgument("cocktailId") { type = NavType.IntType })
        ) {
            CocktailInfoScreen(
                onBackButtonClick = navigateToBack,
                navigateToRedactor = navigateToRedactor
            )
        }
        composable(
            route = "cocktailRedactor?cocktailId={cocktailId}&mode={mode}",
            arguments = listOf(
                navArgument("cocktailId") { type = NavType.IntType},
                navArgument("mode") {type = NavType.StringType}
            )
        ) {
            CocktailRedactorScreen(onBackButtonClick = navigateToBack)
        }
    }
}