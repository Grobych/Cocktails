package com.globa.cocktails.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
                },
            )
        }
    }
}

@Composable
fun CocktailListItem(cocktail: Cocktail) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp)
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

@Composable
fun TagField(list: List<String>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        Modifier.fillMaxWidth().height(100.dp),
        content = {
        items(list) {
            Button(
                onClick = { /*TODO*/ },
                Modifier.size(40.dp,40.dp).padding(4.dp)
            ) {
                Text(text = it, fontSize = 10.sp, modifier = Modifier.fillMaxSize())
            }
        }
    })
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