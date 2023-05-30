package com.globa.cocktails.ui.cocktailredactor

import android.content.res.Configuration
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.globa.cocktails.R
import com.globa.cocktails.datalayer.models.Cocktail
import com.globa.cocktails.ui.cocktaillist.ErrorComposable
import com.globa.cocktails.ui.cocktaillist.LoadingComposable
import com.globa.cocktails.ui.theme.AppTheme
import com.globa.cocktails.ui.theme.DPs
import com.globa.cocktails.ui.theme.Paddings
import com.globa.cocktails.ui.util.BackButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CocktailRedactorScreen(
    viewModel: CocktailRedactorViewModel = hiltViewModel(),
    onBackButtonClick: () -> Unit
) {
    val uiState = viewModel.uiState.collectAsState()
    when (val state = uiState.value) {
        is CocktailRedactorUiState.Loading -> {
            LoadingComposable()
        }
        is CocktailRedactorUiState.Editing -> {
            Scaffold(
                topBar = { RedactorScreenHeader(state.mode, onBackButtonClick) }
            ) {
                RedactorScreenBody(
                    modifier = Modifier
                        .padding(top = it.calculateTopPadding())
                        .verticalScroll(enabled = true, state = ScrollState(0)),
                    cocktail = state.cocktail,
                    mode = state.mode,
                    changeImage = { /*TODO*/ },
                    onItemChange = {/*TODO*/}
                )
            }
        }
        is CocktailRedactorUiState.Error -> {
            ErrorComposable(errorMessage = state.message)
        }
        CocktailRedactorUiState.Saving -> {

        }
    }
}


@Composable
fun RedactorScreenHeader(
    mode: RedactorMode,
    onBackButtonClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(DPs.headerHeight)
            .background(
                color = MaterialTheme.colorScheme.background
            )
            .padding(start = Paddings.large, end = Paddings.large),
    ) {
        BackButton(
            modifier = Modifier.align(Alignment.CenterStart),
            onClickAction = {onBackButtonClick()}
        )
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = when (mode) {
                RedactorMode.ADD -> "Add receipe"
                RedactorMode.EDIT -> "Edit receipe"
            },
            style = MaterialTheme.typography.headlineSmall.plus(
                TextStyle(color = MaterialTheme.colorScheme.onSurface)
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RedactorScreenBody(
    modifier: Modifier = Modifier,
    cocktail: Cocktail,
    mode: RedactorMode,
    changeImage: ()-> Unit,
    onItemChange: (Cocktail)-> Unit
) {
    Surface {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.surface)
                .padding(
                    top = Paddings.largeLarge,
                    bottom = Paddings.largeLarge,
                    start = Paddings.large,
                    end = Paddings.large
                ),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Photo",
                style = MaterialTheme.typography.titleMedium.plus(
                    TextStyle(color = MaterialTheme.colorScheme.onSurfaceVariant)
                )
            )
            RedactorScreenImage(
                modifier = Modifier.padding(top = Paddings.large),
                mode = mode,
                changeImage = changeImage,
                cocktail = cocktail
            )
            OutlinedTextField(
                value = cocktail.drinkName,
                onValueChange = {onItemChange(cocktail.copy(drinkName = it))},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = Paddings.large),
                enabled = mode == RedactorMode.ADD,
                textStyle = MaterialTheme.typography.bodyLarge,
                label = {
                    Text(
                        text = "Name",
                        modifier = Modifier.padding(bottom = Paddings.extraSmall)
                    )
                },
                shape = MaterialTheme.shapes.medium
            )
            EditableIngredientListScreen(
                modifier.padding(top = Paddings.large),
                ingredients = cocktail.ingredients,
                measures = cocktail.measures,
                onIngredientsChange = {},
                onMeasuresChange = {},
                onAddButtonClick = {},
                onRemoveButtonClick = {}
            )
        }
    }

}

@Composable
fun RedactorScreenImage(
    modifier: Modifier = Modifier,
    mode: RedactorMode,
    changeImage: () -> Unit,
    cocktail: Cocktail
) {
    when (mode) {
        RedactorMode.EDIT-> {
            AsyncImage(
                model = cocktail.imageURL,
                contentDescription = cocktail.drinkName,
                modifier = modifier
                    .heightIn(max = 300.dp)
                    .widthIn(max = 220.dp)
                    .clip(
                        RoundedCornerShape(
                            topStart = DPs.largeImageRound,
                            bottomStart = DPs.largeImageRound
                        )
                    )
            )
        }
        RedactorMode.ADD-> {
            IconButton(
                onClick = { changeImage() },
                modifier = modifier
                    .size(220.dp)
                    .clip(
                        RoundedCornerShape(
                            DPs.largeImageRound
                        )
                    )
                    .background(color = MaterialTheme.colorScheme.background)
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_add_2),
                    contentDescription = "Add image",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditableIngredientListScreen(
    modifier: Modifier = Modifier,
    ingredients: List<String>,
    measures: List<String>,
    onIngredientsChange: (List<String>) -> Unit,
    onMeasuresChange: (List<String>) -> Unit,
    onAddButtonClick: () -> Unit,
    onRemoveButtonClick: (Int) -> Unit
) {


    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = "Ingredients",
            style = MaterialTheme.typography.titleMedium
        )
        ingredients.forEachIndexed {i, item ->
            Row(
                modifier = Modifier
                    .padding(top = Paddings.large)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = item,
                    onValueChange = { value ->
                        onIngredientsChange(
                            ingredients.toMutableList().apply { set(i, value) }.toList()
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.7f),
                    label = {
                        Text(
                            text = "Ingredient $i",
                            modifier = Modifier.padding(bottom = Paddings.small)
                        )
                    },
                    singleLine = true,
                    shape = MaterialTheme.shapes.large
                )
                OutlinedTextField(
                    value = if (i in measures.indices) measures[i] else "",
                    onValueChange = { value ->
                        onMeasuresChange(
                            measures.toMutableList().apply { set(i, value) }.toList()
                        )
                    },
                    modifier = Modifier
                        .width(100.dp)
                        .padding(
                            start = Paddings.large,
                        )
                    ,
                    label = {
                        Text(
                            text = "Amount",
                            modifier = Modifier.padding(bottom = Paddings.small),
                            maxLines = 1
                        )
                    },
                    singleLine = true,
                    shape = MaterialTheme.shapes.large
                )
                IconButton(
                    onClick = { onRemoveButtonClick(i) },
                    modifier = Modifier
                        .padding(start = Paddings.large, end = 4.dp)
                        .size(24.dp)
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_remove),
                        contentDescription = "",
                        modifier = Modifier.size(10.dp)
                    )
                }

            }
        }
        Text(
            text = "+ Add ingredient",
            style = MaterialTheme.typography.titleMedium.plus(
                TextStyle(color = MaterialTheme.colorScheme.primary)
            ),
            modifier = Modifier
                .clickable { onAddButtonClick() }
                .padding(top = Paddings.large)
        )
    }



}

val testCocktail = Cocktail(
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

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun RedactorScreenHeaderPreview() {
    AppTheme {
        Surface {
            RedactorScreenHeader(mode = RedactorMode.EDIT) {}
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun RedactorScreenBodyPreview() {

    AppTheme {
        Surface {
            RedactorScreenBody(
                cocktail = testCocktail,
                mode = RedactorMode.ADD,
                changeImage = {},
                onItemChange = {})
        }
    }
}

@Preview(widthDp = 420)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun RedactorIngredientListPreview() {
    AppTheme {
        Surface {
            EditableIngredientListScreen(
                ingredients = testCocktail.ingredients,
                measures = testCocktail.measures,
                onIngredientsChange = {},
                onMeasuresChange = {},
                onAddButtonClick = {},
                onRemoveButtonClick = {}
            )
        }
    }
}