package com.globa.cocktails.ui.cocktailinfo

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.globa.cocktails.R
import com.globa.cocktails.datalayer.models.Cocktail
import com.globa.cocktails.ui.theme.AppTheme

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

}

@Composable
private fun Header() {

}

@Composable
private fun Ingredients(cocktail: Cocktail) {

}

@Composable fun Instructions(instructions: String, modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
            color = MaterialTheme.colorScheme.primary,
            shape = MaterialTheme.shapes.large
        )
    ) {
        Text(
            text = "Receipe",
            modifier = Modifier
                .padding(top = 16.dp, start = 16.dp, end = 16.dp),
            style = MaterialTheme.typography.titleMedium.plus(
                TextStyle(color = MaterialTheme.colorScheme.onPrimary)
            )
        )
        Text(
            text = instructions,
            modifier = Modifier
                .padding(top = 12.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
            style = MaterialTheme.typography.bodyMedium.plus(
                TextStyle(color = MaterialTheme.colorScheme.primaryContainer)
            )
        )
    }
}

@Composable
fun CocktailError(errorMessage: String) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = errorMessage, modifier = Modifier.align(Alignment.Center))
    }
}

private val testCocktail = Cocktail(
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

//@Composable
//@Preview
//fun CocktailInfoPreview() {
//    CocktailInfo(cocktail = cocktail)
//}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview
fun InstructionsPreview() {
    val instructions = testCocktail.instructions
    AppTheme {
        Surface {
            Instructions(instructions = instructions, modifier = Modifier.padding(16.dp))
        }
    }
}