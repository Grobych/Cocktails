package com.globa.cocktails.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.globa.cocktails.R
import com.globa.cocktails.datalayer.models.Cocktail
import com.globa.cocktails.ui.viewmodels.CocktailListViewModel

@Composable
fun CocktailListScreen(viewModel: CocktailListViewModel) {
    val uiState = viewModel.uiState.collectAsState()
    viewModel.loadCocktails()
    when (uiState.value.status) {
        UiStateStatus.LOADING -> {

        }
        UiStateStatus.ERROR -> {

        }
        UiStateStatus.DONE -> {
            LazyColumn(
                content = {
                    items(uiState.value.cocktailList) {
                        CocktailListItem(cocktail = it)
                    }
                }
            )
        }
    }
}

@Composable
fun CocktailListItem(cocktail: Cocktail) {
    Row {
        AsyncImage(
            model = cocktail.imageURL,
            contentDescription = cocktail.drinkName,
            placeholder = painterResource(id = R.drawable.loading_img)
        )
        Text(text = cocktail.drinkName)
    }
}

@Preview
@Composable
fun CocktailListItemPreview(){
    val cocktail = Cocktail(
        drinkName = "Margarita",
        imageURL = "http://www.thecocktaildb.com/images/media/drink/wpxpvu1439905379.jpg",
        ingredients = listOf("Tequila","Triple sec","Lime juice","Salt")
    )

    CocktailListItem(cocktail = cocktail)
}