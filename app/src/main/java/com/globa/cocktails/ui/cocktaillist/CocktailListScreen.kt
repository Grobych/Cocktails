package com.globa.cocktails.ui.cocktaillist

import android.content.res.Configuration
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.globa.cocktails.R
import com.globa.cocktails.datalayer.models.Cocktail
import com.globa.cocktails.ui.UiStateStatus
import com.globa.cocktails.ui.theme.AppTheme
import com.globa.cocktails.ui.util.AddButton
import com.globa.cocktails.ui.util.CustomSearchField
import com.globa.cocktails.ui.util.LoadingAnimation
import com.globa.cocktails.ui.util.MenuButton
import com.globa.cocktails.ui.util.TagButton

@Composable
fun CocktailListScreen(
    viewModel: CocktailListViewModel = hiltViewModel(),
    onItemClickAction: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val filterUiState by viewModel.filterUiState.collectAsState()

    val onFilterChangeAction: (TextFieldValue) -> Unit = { value ->
        val text = value.text
        if (text.takeLast(1) == " ") {
            val tag = text.take(text.length - 1)
            viewModel.addFilterTag(tag)
            viewModel.updateFilterLine(TextFieldValue(""))
        } else {
            viewModel.updateFilterLine(value)
        }
    }

    val removeTagAction: (String) -> Unit = {
        viewModel.removeFilterTag(it)
    }

    val addTagAction: (String) -> Unit = {
        viewModel.addFilterTag(it)
    }

    val onRandomButtonAction: () -> Unit = {
        onItemClickAction(viewModel.getRandomCocktail())
    }

    when (uiState.status) {
        UiStateStatus.LOADING -> {
            LoadingComposable()
        }
        UiStateStatus.ERROR -> {
            ErrorComposable(errorMessage = uiState.errorMessage)
        }
        UiStateStatus.DONE -> {
            Column(modifier = Modifier.fillMaxSize()) {
                Header(filterUiState = filterUiState, onFilterChangeAction = onFilterChangeAction, onTagClicked = removeTagAction)
                if (uiState.cocktailList.isNotEmpty()) {
                    CocktailList(list = uiState.cocktailList, onItemClickAction = onItemClickAction, onTagClicked = addTagAction)
                } else EmptyList()
            }
        }
    }
}

@Composable
fun Header(
    filterUiState: CocktailFilterUiState,
    onFilterChangeAction: (TextFieldValue) -> Unit,
    onTagClicked: (String) -> Unit
) {
    Surface {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CustomSearchField(
                modifier = Modifier.fillMaxWidth(0.7f),
                tags = filterUiState.tags,
                text = filterUiState.line,
                onTextChanged = onFilterChangeAction,
                onTagClicked = onTagClicked
            )
            AddButton(
                onClickAction = {}
            )
            MenuButton()
        }
    }

}

@Composable
fun CocktailList(list: List<Cocktail>, onItemClickAction: (String) -> Unit, onTagClicked: (String) -> Unit) {
    LazyColumn(
        contentPadding = PaddingValues(bottom = 1.dp)
    ) {
        items(list) {
            CocktailListItem(
                cocktail = it,
                onItemClickAction = {onItemClickAction(it.id)},
                onTagClicked = onTagClicked
            )
        }
    }
}

@Composable
fun CocktailListItem(cocktail: Cocktail, onItemClickAction: () -> Unit, onTagClicked: (String) -> Unit) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .background(color = MaterialTheme.colorScheme.surface) //TODO: add elevation table
            .clickable(onClick = onItemClickAction),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        AsyncImage(
            model = cocktail.imageURL,
            contentDescription = cocktail.drinkName,
            placeholder = painterResource(id = R.drawable.loading_img),
            error = painterResource(id = R.drawable.broken_image),
            modifier = Modifier
                .size(80.dp)
                .align(Alignment.CenterVertically)
                .padding(start = 16.dp)
                .clip(MaterialTheme.shapes.large)
        )
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 20.dp, bottom = 20.dp, start = 10.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(start = 10.dp),
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Row {
                        Text(text = cocktail.drinkName, fontSize = MaterialTheme.typography.titleMedium.fontSize)
                        //star
                    }
                    Row {
                        TagField(list = cocktail.ingredients, onItemClickAction = onTagClicked)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TagField(
    list: List<String>,
    onItemClickAction: (String) -> Unit
) {
    FlowRow(
        modifier = Modifier.horizontalScroll(state = ScrollState(0))
    ) {
        list.forEach {
            TagButton(
                text = it,
                modifier = Modifier.padding(end = 10.dp),
                onClickAction = {onItemClickAction(it)},
            )
        }
    }
}

@Composable
fun LoadingComposable() {
    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        LoadingAnimation()
    }
}

@Composable
fun ErrorComposable(errorMessage: String) {
    Row (
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "ERROR: $errorMessage")
    }
}

@Composable
fun EmptyList() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = stringResource(R.string.empty_container_to_show), modifier = Modifier.align(Alignment.Center))
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CocktailListItemPreview(){
    val cocktail = Cocktail(
        drinkName = "Margarita",
        imageURL = "http://www.thecocktaildb.com/images/media/drink/wpxpvu1439905379.jpg",
        ingredients = listOf("Tequila","Triple sec","Lime juice","Agava", "Salt", "Vodka")
    )

    AppTheme {
        Surface {
            CocktailListItem(cocktail = cocktail, onTagClicked = {}, onItemClickAction = {})
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HeaderPreview() {
    val filterUiState = CocktailFilterUiState(
        TextFieldValue(""),
        emptyList()
    )
    AppTheme {
        Surface {
            Header(
                filterUiState = filterUiState,
                onFilterChangeAction = {},
                onTagClicked = {}
            )
        }
    }
}