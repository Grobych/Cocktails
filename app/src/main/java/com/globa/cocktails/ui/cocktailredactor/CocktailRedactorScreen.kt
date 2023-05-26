package com.globa.cocktails.ui.cocktailredactor

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
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
//            Scaffold(
//                topBar = RedactorScreenHeader(state.mode, onBackButtonClick)
//            ) {
//                RedactorScreenBody(
//                    modifier = Modifier
//                        .padding(top = it.calculateTopPadding())
//                        .verticalScroll(enabled = true, state = ScrollState(0))
//                )
//            }
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