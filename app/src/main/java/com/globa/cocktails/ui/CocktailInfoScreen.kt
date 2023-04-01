package com.globa.cocktails.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.globa.cocktails.ui.viewmodels.CocktailViewModel
import coil.compose.AsyncImage
import com.globa.cocktails.datalayer.models.Cocktail

@Composable
fun CocktailInfoScreen(
    viewModel: CocktailViewModel = hiltViewModel()
) {

    val cocktail by viewModel.cocktail.collectAsState()

    CocktailInfo(cocktail = cocktail)
}

@Composable
fun CocktailInfo(cocktail: Cocktail) {
    Row(Modifier.fillMaxWidth()) {
        AsyncImage(
            model = cocktail.imageURL, 
            contentDescription = cocktail.drinkName,
            modifier = Modifier.size(width = 180.dp, height = 270.dp)
        )
        Text(text = cocktail.drinkName)
    }
    Row(Modifier.fillMaxWidth()) {
        Text(text = cocktail.instructions)
    }
}