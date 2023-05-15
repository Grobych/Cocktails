package com.globa.cocktails.ui.cocktailinfo

import android.content.res.Configuration
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.globa.cocktails.ui.theme.DPs.headerHeight
import com.globa.cocktails.ui.theme.DPs.largeImageRound
import com.globa.cocktails.ui.theme.Paddings
import com.globa.cocktails.ui.util.AddButton
import com.globa.cocktails.ui.util.BackButton
import com.globa.cocktails.ui.util.FavoriteButton
import com.globa.cocktails.ui.util.MenuButton
import com.globa.cocktails.ui.util.TagButton

@Composable
fun CocktailInfoScreen(
    viewModel: CocktailViewModel = hiltViewModel(),
    onBackButtonClick: () -> Unit
) {

    val uiState by viewModel.uiState.collectAsState()
    val onFavoriteButtonClick: () -> Unit = {
        if (uiState is CocktailUiState.Success) {
            val cocktail = (uiState as CocktailUiState.Success).cocktail
            viewModel.updateCocktail(cocktail.copy(isFavorite = cocktail.isFavorite.not()))
        }
    }

    CocktailScreenSelect(uiState = uiState, onFavoriteButtonClick = onFavoriteButtonClick, onBackButtonClick = onBackButtonClick)
}

@Composable
fun CocktailScreenSelect(uiState: CocktailUiState, onFavoriteButtonClick: () -> Unit, onBackButtonClick: () -> Unit) {
    when (uiState) {
        is CocktailUiState.Loading -> CocktailLoading()
        is CocktailUiState.Success -> CocktailInfo(cocktail = uiState.cocktail, onFavoriteButtonClick = onFavoriteButtonClick, onBackButtonClick = onBackButtonClick)
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
fun CocktailInfo(cocktail: Cocktail, onFavoriteButtonClick: () -> Unit, onBackButtonClick: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        Header(cocktailName = cocktail.drinkName, onFavoriteButtonClick = onFavoriteButtonClick, onBackButtonClick = onBackButtonClick, isFavorited = cocktail.isFavorite)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = Paddings.medium, start = Paddings.large, bottom = Paddings.large)
                .background(color = MaterialTheme.colorScheme.surface)
            ,
            verticalAlignment = Alignment.Top
        ) {
            Ingredients(
                ingredients = cocktail.ingredients,
                measures = cocktail.measures,
                modifier = Modifier
                    .fillMaxWidth(0.6f)
            )
            AsyncImage(
                model = cocktail.imageURL,
                contentDescription = cocktail.drinkName,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(287.dp)
                    .padding(start = Paddings.large)
                    .clip(
                        RoundedCornerShape(
                            topStart = largeImageRound,
                            bottomStart = largeImageRound
                        )
                    )
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = Paddings.large)
        ) {
            listOf(cocktail.drinkGlass, cocktail.drinkCategory).forEach {
                TagButton(text = it, modifier = Modifier.padding(end = Paddings.medium)) {
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
private fun Header(
    cocktailName: String,
    modifier: Modifier = Modifier,
    onFavoriteButtonClick: () -> Unit,
    onBackButtonClick: () -> Unit,
    isFavorited: Boolean
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(headerHeight)
                .padding(start = Paddings.large, end = Paddings.large),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            BackButton(onClickAction = { onBackButtonClick() })
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = cocktailName,
                    modifier = Modifier
                        .widthIn(max = 225.dp)
                        .padding(start = Paddings.medium, end = Paddings.medium)
                        .horizontalScroll(state = ScrollState(0))
                    ,
                    style = MaterialTheme.typography.headlineSmall.plus(
                        TextStyle(color = MaterialTheme.colorScheme.onSurface)
                    ),
                    maxLines = 1,
                    softWrap = false
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AddButton(onClickAction = { /*TODO*/ }, modifier = Modifier.padding(end = Paddings.large))
                    FavoriteButton(onClickAction = { onFavoriteButtonClick() }, isFavorited = isFavorited)
                    MenuButton(onClickAction = { /*TODO*/ }, modifier = Modifier.padding(start = Paddings.large))
                }
            }
        }
        Divider(
            color = MaterialTheme.colorScheme.surfaceVariant,
            thickness = 1.dp,
            modifier = Modifier
                .padding(start = Paddings.large, end = Paddings.large)
        )
    }
}

@Composable
private fun Ingredients(ingredients: List<String>, measures: List<String>, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = "Ingredients",
            modifier = Modifier.padding(start = Paddings.medium, top = Paddings.medium),
            style = MaterialTheme.typography.titleMedium.plus(
                TextStyle(color = MaterialTheme.colorScheme.primary)
            )
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = Paddings.medium, end = Paddings.medium, bottom = Paddings.medium)
        ) {
            for (i in 0 .. ingredients.lastIndex) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = Paddings.small),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = ingredients[i],
                        style = MaterialTheme.typography.bodyMedium.plus(
                            TextStyle(color = MaterialTheme.colorScheme.onSurfaceVariant)
                        )
                    )
                    Text(
                        text = if (i <= measures.lastIndex) measures[i] else "",
                        style = MaterialTheme.typography.bodyMedium.plus(
                            TextStyle(color = MaterialTheme.colorScheme.onSurfaceVariant)
                        )
                    )
                }
                Divider()
            }
        }
    }
}

@Composable fun Instructions(instructions: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = MaterialTheme.shapes.large
            )
    ) {
        Text(
            text = "Receipe",
            modifier = Modifier
                .padding(top = Paddings.small, start = Paddings.large),
            style = MaterialTheme.typography.titleMedium.plus(
                TextStyle(color = MaterialTheme.colorScheme.primary)
            )
        )
        Text(
            text = instructions,
            modifier = Modifier
                .padding(top = Paddings.medium, start = Paddings.large, end = Paddings.extraLarge, bottom = Paddings.medium),
            style = MaterialTheme.typography.bodyMedium.plus(
                TextStyle(color = MaterialTheme.colorScheme.onSurfaceVariant)
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
            Header(cocktailName = name, modifier = Modifier.width(420.dp), onFavoriteButtonClick = {}, onBackButtonClick = {}, isFavorited = false)
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
            CocktailInfo(cocktail = testCocktail, onFavoriteButtonClick = {}, onBackButtonClick = {})
        }
    }
}