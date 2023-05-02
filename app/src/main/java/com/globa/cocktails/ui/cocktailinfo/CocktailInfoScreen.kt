package com.globa.cocktails.ui.cocktailinfo

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.globa.cocktails.R
import com.globa.cocktails.datalayer.models.Cocktail
import com.globa.cocktails.ui.theme.AppTheme
import com.globa.cocktails.ui.theme.Paddings
import com.globa.cocktails.ui.util.SemiCircleShape
import com.globa.cocktails.ui.util.TagButton

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
    Column(modifier = Modifier.fillMaxSize()) {
        Header(cocktailName = cocktail.drinkName)
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Ingredients(
                ingredients = cocktail.ingredients,
                measures = cocktail.measures,
                modifier = Modifier.fillMaxWidth(0.6f).padding(Paddings.medium)
            )
            AsyncImage(
                model = cocktail.imageURL,
                contentDescription = cocktail.drinkName,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(top = Paddings.medium)
                    .height(225.dp)
                    .clip(SemiCircleShape)
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(start = Paddings.large)
        ) {
            listOf(cocktail.drinkGlass, cocktail.drinkCategory).forEach {
                TagButton(text = it, modifier = Modifier.padding(end = Paddings.small)) {
                    //TODO: onClick
                }
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Instructions(instructions = cocktail.instructions, modifier = Modifier.padding(Paddings.large))
        }
    }
}

@Composable
private fun Header(cocktailName: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(75.dp)
    ) {
        Text(
            text = cocktailName,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = Paddings.extraLarge),
            style = MaterialTheme.typography.headlineMedium.plus(
                TextStyle(color = MaterialTheme.colorScheme.onSurface)
            )
        )
//        Row(
//            modifier = Modifier.align(Alignment.CenterEnd)
//        ) {
////            //TODO: Add elevation
////            AddButton(modifier = Modifier.padding(end = 10.dp)) {
////                // TODO: FavoriteButton
////            }
////            AddButton(modifier = Modifier.padding(end = 10.dp)) {
////
////            }
////            MenuButton(modifier = Modifier.padding(end = 16.dp))
//        }
        Divider(
            color = MaterialTheme.colorScheme.surfaceVariant,
            thickness = 1.dp,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(start = Paddings.large, end = Paddings.large)
        )
    }
}

@Composable
private fun Ingredients(ingredients: List<String>, measures: List<String>, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = MaterialTheme.shapes.large
            )
    ) {
        Text(
            text = "Ingredients",
            modifier = Modifier.padding(Paddings.medium),
            style = MaterialTheme.typography.titleMedium.plus(
                TextStyle(color = MaterialTheme.colorScheme.primary)
            )
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = Paddings.medium)
        ) {
            Column(
                modifier = Modifier.padding(start = Paddings.medium)
            ) {
                ingredients.forEach {
                    Text(
                        text = it,
                        modifier = Modifier
                            .padding(top = Paddings.extraSmall),
                        style = MaterialTheme.typography.bodyMedium.plus(
                            TextStyle(color = MaterialTheme.colorScheme.onSurfaceVariant)
                        )
                    )
                }
            }
            Column(
                horizontalAlignment = Alignment.End,
                modifier = Modifier.padding(end = Paddings.medium)
            ) {
                measures.forEach {
                    Text(
                        text = it,
                        modifier = Modifier
                            .padding(top = Paddings.extraSmall),
                        style = MaterialTheme.typography.bodyMedium.plus(
                            TextStyle(color = MaterialTheme.colorScheme.onSurfaceVariant)
                        )
                    )
                }
            }
        }
    }
}

@Composable fun Instructions(instructions: String, modifier: Modifier = Modifier) {
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

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview
fun HeaderPreview() {
    val name = testCocktail.drinkName
    AppTheme {
        Surface {
            Header(cocktailName = name, modifier = Modifier.width(360.dp))
        }
    }
}

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

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview
fun IngredientsPreview() {
    val ingredients = testCocktail.ingredients
    val measures = testCocktail.measures
    AppTheme {
        Surface {
            Ingredients(
                ingredients = ingredients, measures = measures,
                modifier = Modifier
                    .padding(16.dp)
                    .width(150.dp)
            )
        }
    }
}

@Composable
@Preview
fun CocktailInfoPreview() {
    AppTheme {
        Surface {
            CocktailInfo(cocktail = testCocktail)
        }
    }
}