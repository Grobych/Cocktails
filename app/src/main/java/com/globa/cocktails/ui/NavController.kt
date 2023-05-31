package com.globa.cocktails.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.globa.cocktails.ui.cocktailinfo.CocktailInfoScreen
import com.globa.cocktails.ui.cocktaillist.CocktailListScreen
import com.globa.cocktails.ui.cocktailredactor.CocktailRedactorScreen
import com.globa.cocktails.ui.cocktailredactor.RedactorMode

@Composable
fun NavController() {
    val navController = rememberNavController()

    val navigateToCocktail: (String) -> Unit = { cocktailId ->
        navController.navigate("cocktailInfo?cocktailId=${cocktailId}")
    }
    val navigateToRedactor: (String, RedactorMode) -> Unit = { cocktailId, mode ->
        navController.navigate("cocktailRedactor?cocktailId=${cocktailId}&mode=${mode.name}")
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
            arguments = listOf(navArgument("cocktailId") { type = NavType.StringType })
        ) {
            CocktailInfoScreen(
                onBackButtonClick = navigateToBack,
                navigateToRedactor = navigateToRedactor
            )
        }
        composable(
            route = "cocktailRedactor?cocktailId={cocktailId}&mode={mode}",
            arguments = listOf(
                navArgument("cocktailId") { type = NavType.StringType},
                navArgument("mode") {type = NavType.StringType}
            )
        ) {
            CocktailRedactorScreen(onBackButtonClick = navigateToBack)
        }
    }
}