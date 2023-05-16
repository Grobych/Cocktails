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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import com.globa.cocktails.domain.RandomCocktailUseCase
import com.globa.cocktails.ui.UiStateStatus
import com.globa.cocktails.ui.theme.AppTheme
import com.globa.cocktails.ui.theme.DPs
import com.globa.cocktails.ui.theme.DPs.headerHeight
import com.globa.cocktails.ui.theme.Paddings
import com.globa.cocktails.ui.util.AddButton
import com.globa.cocktails.ui.util.CustomSearchField
import com.globa.cocktails.ui.util.FavoriteButton
import com.globa.cocktails.ui.util.FooterButton
import com.globa.cocktails.ui.util.LoadingAnimation
import com.globa.cocktails.ui.util.MenuButton
import com.globa.cocktails.ui.util.TagButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CocktailListScreen(
    viewModel: CocktailListViewModel = hiltViewModel(),
    onItemClickAction: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val filterUiState by viewModel.filterUiState.collectAsState()
    val selectorUiState by viewModel.selectorUiState.collectAsState()
    val randomCocktailUseCase = RandomCocktailUseCase()

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

    val updateCocktail: (Cocktail) -> Unit = {
        viewModel.updateCocktail(it.copy(isFavorite = it.isFavorite.not()))
    }

    when (uiState.status) {
        UiStateStatus.LOADING -> {
            LoadingComposable()
        }
        UiStateStatus.ERROR -> {
            ErrorComposable(errorMessage = uiState.errorMessage)
        }
        UiStateStatus.DONE -> {
            val onRandomButtonAction: () -> Unit = {
                onItemClickAction(randomCocktailUseCase(uiState.cocktailList))
            }
            Scaffold(
                topBar = {
                    Header(filterUiState = filterUiState, onFilterChangeAction = onFilterChangeAction, onTagClicked = removeTagAction)
                },
                bottomBar = {
                    Footer(
                        selectorUiState = selectorUiState,
                        onAllReceipesClicked = { viewModel.selectorChanged(FooterSelector.ALL_COCKTAILS) },
                        onMyReceipesClicked = { viewModel.selectorChanged(FooterSelector.MY_COCKTAILS) },
                        onFavoriteReceipesClicked = { viewModel.selectorChanged(FooterSelector.FAVORITE_COCKTAILS) },
                        onRandomReceipeClicked = onRandomButtonAction
                    )
                },
                content = { paddingValues ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(
                                bottom = paddingValues.calculateBottomPadding(),
                                top = paddingValues.calculateTopPadding()
                            )
                    ) {
                        if (uiState.cocktailList.isNotEmpty())
                            CocktailList(list = uiState.cocktailList, onItemClickAction = onItemClickAction, onTagClicked = addTagAction, onFavoriteClicked = updateCocktail)
                        else EmptyList()
                    }
                }
            )
        }
    }
}

@Composable
fun Header(
    filterUiState: CocktailFilterUiState,
    onFilterChangeAction: (TextFieldValue) -> Unit,
    onTagClicked: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(headerHeight)
            .background(color = MaterialTheme.colorScheme.background)
            .padding(
                start = Paddings.large,
                end = Paddings.large
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        CustomSearchField(
            modifier = Modifier
                .width(280.dp),
            tags = filterUiState.tags,
            text = filterUiState.line,
            onTextChanged = onFilterChangeAction,
            onTagClicked = onTagClicked
        )
        AddButton(
            onClickAction = {}
        )
        MenuButton(
            onClickAction = {}
        )
    }
}

@Composable
fun CocktailList(list: List<Cocktail>, onItemClickAction: (String) -> Unit, onTagClicked: (String) -> Unit, onFavoriteClicked: (Cocktail) -> Unit) {
    LazyColumn(
        contentPadding = PaddingValues(bottom = DPs.line)
    ) {
        items(
            items = list,
            key = { it.id }
        ) {
            CocktailListItem(
                cocktail = it,
                onItemClickAction = {onItemClickAction(it.id)},
                onTagClicked = onTagClicked,
                onFavoriteClicked = onFavoriteClicked
            )
            Divider(modifier = Modifier.padding(start = Paddings.large, end = Paddings.large), thickness = DPs.line)
        }
    }
}

@Composable
fun CocktailListItem(
    cocktail: Cocktail,
    onItemClickAction: () -> Unit,
    onTagClicked: (String) -> Unit,
    onFavoriteClicked: (Cocktail) -> Unit
) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .background(color = MaterialTheme.colorScheme.surface) //TODO: add elevation table
            .padding(start = Paddings.large)
            .clickable(onClick = onItemClickAction),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = cocktail.imageURL,
            contentDescription = cocktail.drinkName,
            placeholder = painterResource(id = R.drawable.loading_img),
            error = painterResource(id = R.drawable.broken_image),
            modifier = Modifier
                .size(106.dp)
                .align(Alignment.CenterVertically)
                .clip(MaterialTheme.shapes.large)
        )
        Box(modifier = Modifier.padding(start = Paddings.small)){
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Row {
                    FavoriteButton(
                        isFavorited = cocktail.isFavorite,
                        onClickAction = { onFavoriteClicked(cocktail) }
                    )
                    Text(
                        text = cocktail.drinkName,
                        modifier = Modifier.padding(start = Paddings.small),
                        style = MaterialTheme.typography.titleMedium)
                    //star
                }
                Row(
                    modifier = Modifier.padding(top = Paddings.medium)
                ) {
                    TagField(list = cocktail.ingredients, onItemClickAction = onTagClicked)
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
                modifier = Modifier.padding(end = Paddings.medium),
                onClickAction = {onItemClickAction(it)},
            )
        }
    }
}

@Composable
fun Footer(
    selectorUiState: CocktailSelectorUiState,
    onAllReceipesClicked: () -> Unit,
    onMyReceipesClicked: () -> Unit,
    onFavoriteReceipesClicked: () -> Unit,
    onRandomReceipeClicked: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .background(color = MaterialTheme.colorScheme.background),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        val selectedColor = MaterialTheme.colorScheme.onBackground
        val unSelectedColor = MaterialTheme.colorScheme.primary
        FooterButton(
            onClickAction = onAllReceipesClicked,
            icon = R.drawable.ic_all_receipes,
            iconColor = if (selectorUiState.isAllCocktailsSelected) selectedColor else unSelectedColor,
            text = "All receipes"
        )
        FooterButton(
            onClickAction = onMyReceipesClicked,
            icon = R.drawable.ic_my_receipes,
            iconColor = if (selectorUiState.isMyCocktailSelected) selectedColor else unSelectedColor,
            text = "My receipes"
        )
        FooterButton(
            onClickAction = onFavoriteReceipesClicked,
            icon = R.drawable.ic_favorite_receipes,
            iconColor = if (selectorUiState.isFavoriteSelected) selectedColor else unSelectedColor,
            text = "Favorite"
        )
        FooterButton(
            onClickAction = onRandomReceipeClicked,
            icon = R.drawable.ic_random_receipe,
            text = "Random"
        )
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
        Surface(
            modifier = Modifier.width(480.dp)
        ) {
            CocktailListItem(cocktail = cocktail, onTagClicked = {}, onItemClickAction = {}, onFavoriteClicked = {})
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
        Surface(
            modifier = Modifier.width(480.dp)
        ) {
            Header(
                filterUiState = filterUiState,
                onFilterChangeAction = {},
                onTagClicked = {}
            )
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun FooterPreview() {
    val selectorUiState = CocktailSelectorUiState()
    AppTheme {
        Surface {
            Footer(
                selectorUiState = selectorUiState,
                onAllReceipesClicked = { },
                onMyReceipesClicked = { },
                onFavoriteReceipesClicked = { },
                onRandomReceipeClicked = {}
            )
        }
    }
}