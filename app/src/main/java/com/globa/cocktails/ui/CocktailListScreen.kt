package com.globa.cocktails.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.globa.cocktails.R
import com.globa.cocktails.datalayer.models.Cocktail
import com.globa.cocktails.ui.util.LoadingAnimation
import com.globa.cocktails.ui.viewmodels.CocktailListViewModel

@Composable
fun CocktailListScreen(
    viewModel: CocktailListViewModel = hiltViewModel(),
    onItemClickAction: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    val onFilterChangeAction: (String) -> Unit = {
        viewModel.updateFilter(it)
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
                Header(filterValue = uiState.filterUiState.filter, onFilterChangeAction = onFilterChangeAction, onRandomButtonAction = onRandomButtonAction)
                if (uiState.cocktailList.isNotEmpty()) {
                    CocktailList(list = uiState.cocktailList, onItemClickAction = onItemClickAction)
                } else EmptyList()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Header(
    filterValue: String,
    onFilterChangeAction: (String) -> Unit,
    onRandomButtonAction: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TextField(value = filterValue, onValueChange = {onFilterChangeAction(it)})
        Button(onClick = { onRandomButtonAction() }) {
            Text(text = stringResource(id = R.string.get_random_button_text))
        }
    }
}

@Composable
fun CocktailList(list: List<Cocktail>, onItemClickAction: (String) -> Unit) {
    LazyColumn {
        items(list) {
            CocktailListItem(cocktail = it) { onItemClickAction(it.id) }
        }
    }
}

@Composable
fun CocktailListItem(cocktail: Cocktail, onItemClickAction: () -> Unit) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp)
            .clickable(onClick = onItemClickAction)
    ) {
        AsyncImage(
            model = cocktail.imageURL,
            contentDescription = cocktail.drinkName,
            placeholder = painterResource(id = R.drawable.loading_img),
            error = painterResource(id = R.drawable.broken_image),
            modifier = Modifier.size(130.dp)
        )
        Column {
            Text(
                text = cocktail.drinkName,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp),
                fontSize = 16.sp
            )
            TagField(cocktail.ingredients)
        }

    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TagField(list: List<String>) {
    FlowRow(
        maxItemsInEachRow = 3
    ) {
        list.forEach {
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .padding(top = 5.dp, end = 5.dp),
            ) {
                Text(text = it, fontSize = 10.sp)
            }
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
@Composable
fun CocktailListItemPreview(){
    val cocktail = Cocktail(
        drinkName = "Margarita",
        imageURL = "http://www.thecocktaildb.com/images/media/drink/wpxpvu1439905379.jpg",
        ingredients = listOf("Tequila","Triple sec","Lime juice","Salt")
    )

    CocktailListItem(cocktail = cocktail) {}
}