package com.globa.cocktails.ui.cocktailinfo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.globa.cocktails.R
import com.globa.cocktails.datalayer.models.Cocktail

@Composable
fun CocktailInfoScreen(
    viewModel: CocktailViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()
    CocktailScreenSelect(uiState = uiState)
}

@Composable
fun CocktailScreenSelect(uiState: CocktailUiState) {
    when (uiState) {
        is CocktailUiState.Loading -> CocktailLoading()
        is CocktailUiState.Success -> CocktailInfo(cocktail = uiState.cocktail)
        is CocktailUiState.Error -> CocktailError(errorMessage = uiState.message)
    }
}

@Composable
fun CocktailLoading() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = stringResource(R.string.loading_string), modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
fun CocktailInfo(cocktail: Cocktail) {
    Column {
        Row(Modifier.fillMaxWidth()) {
            AsyncImage(
                model = cocktail.imageURL,
                contentDescription = cocktail.drinkName,
                modifier = Modifier
                    .size(width = 180.dp, height = 270.dp)
                    .padding(10.dp)
            )
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = cocktail.drinkName,
                    fontSize = 26.sp,
                    modifier = Modifier
                        .padding(20.dp)
                )
                CocktailIngredients(cocktail)
            }
        }
        Row(Modifier.fillMaxWidth()) {
            Text(
                text = cocktail.instructions,
                modifier = Modifier.padding(20.dp),
                fontSize = 20.sp
            )
        }
    }
}

@Composable
fun CocktailIngredients(cocktail: Cocktail) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(end = 10.dp), horizontalArrangement = Arrangement.SpaceBetween) {
        LazyColumn(horizontalAlignment = Alignment.Start) {
            items(cocktail.ingredients) {
                Text(text = it)
            }
        }
        LazyColumn(horizontalAlignment = Alignment.End) {
            items(cocktail.measures) {
                Text(text = it)
            }
        }
    }
}

@Composable
fun CocktailError(errorMessage: String) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = errorMessage, modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
@Preview
fun CocktailInfoPreview() {
    val cocktail = Cocktail(
        id = "id",
        drinkNumber = 362,
        drinkName = "Margarita",
        alcohol = true,
        drinkCategory = "Ordinary drinks",
        imageURL = "http://www.thecocktaildb.com/images/media/drink/wpxpvu1439905379.jpg",
        drinkGlass = "Cocktail glass",
        ingredients = listOf("Tequila","Triple sec","Lime juice","Salt"),
        measures = listOf("1 1/2 oz","1/2 oz","1 oz"),
        instructions = "Rub the rim of the glass with the lime slice to make the salt stick to it. Take care to moisten only the outer rim and sprinkle the salt on it. The salt should present to the lips of the imbiber and never mix into the cocktail. Shake the other ingredients with ice, then carefully pour into the glass."
    )
    CocktailInfo(cocktail = cocktail)
}