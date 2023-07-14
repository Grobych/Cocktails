package com.globa.cocktails.feature.edit.api

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.globa.cocktails.domain.edit.RecipeEditable
import com.globa.cocktails.domain.edit.RemoveIngredientUseCase
import com.globa.cocktails.feature.edit.R
import com.globa.cocktails.feature.edit.internal.CocktailRedactorUiState
import com.globa.cocktails.feature.edit.internal.CocktailRedactorViewModel
import com.globa.cocktails.feature.edit.internal.RedactorMode
import com.globa.cocktails.ui.theme.DPs
import com.globa.cocktails.ui.util.BackButton
import com.globa.cocktails.ui.util.ErrorComposable
import com.globa.cocktails.ui.util.LoadingComposable
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CocktailRedactorScreen(
    viewModel: CocktailRedactorViewModel = hiltViewModel(),
    onBackButtonClick: () -> Unit
) {
    val uiState = viewModel.uiState.collectAsState()
    val errorFieldsState = viewModel.errorState.collectAsState()
    val showSaveDialog = viewModel.showSaveDialog.collectAsState()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val onSaveAccept: () -> Unit = {
        scope.launch {
            viewModel.saveApply()
            Toast.makeText(
                context,
                "Cocktail Saved!",
                Toast.LENGTH_SHORT
            ).show()
            onBackButtonClick()
        }
    }
    val onSaveDismiss: () -> Unit = {viewModel.saveDismiss()}

    val onItemChange: (RecipeEditable) -> Unit = {
        viewModel.updateEditable(editable = it)
    }
    when (val state = uiState.value) {
        is CocktailRedactorUiState.Loading -> {
            LoadingComposable()
        }
        is CocktailRedactorUiState.Editing -> {
            if (showSaveDialog.value) SaveDialog(
                onAccept = onSaveAccept,
                onDismiss = onSaveDismiss
            )
            Scaffold(
                topBar = { RedactorScreenHeader(state.mode, onBackButtonClick) },
                floatingActionButton = { SaveFloatingButton {
                    viewModel.requestToSaveRecipe()
                }
                }
            ) {
                RedactorScreenBody(
                    modifier = Modifier
                        .padding(top = it.calculateTopPadding()),
                    cocktail = state.cocktail,
                    mode = state.mode,
                    isNameError = errorFieldsState.value.isNameError,
                    isIngredientsError = errorFieldsState.value.isIngredientError,
                    isInstructionError = errorFieldsState.value.isInstructionsError,
                    changeImage = { /*TODO*/ },
                    onItemChange = onItemChange
                )
            }
        }
        is CocktailRedactorUiState.Error -> {
            ErrorComposable(errorMessage = state.message)
        }
        CocktailRedactorUiState.Saving -> {
            LoadingComposable()
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
            .padding(
                start = com.globa.cocktails.ui.theme.Paddings.large,
                end = com.globa.cocktails.ui.theme.Paddings.large
            ),
    ) {
        BackButton(
            modifier = Modifier.align(Alignment.CenterStart),
            onClickAction = {onBackButtonClick()}
        )
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = when (mode) {
                RedactorMode.ADD -> "Add recipe"
                RedactorMode.EDIT -> "Edit recipe"
            },
            style = MaterialTheme.typography.headlineSmall.plus(
                TextStyle(color = MaterialTheme.colorScheme.onSurface)
            )
        )
    }
}

@Composable
fun RedactorScreenBody(
    modifier: Modifier = Modifier,
    cocktail: RecipeEditable,
    mode: RedactorMode,
    isNameError: Boolean,
    isIngredientsError: List<Boolean>,
    isInstructionError: Boolean,
    changeImage: ()-> Unit,
    onItemChange: (RecipeEditable)-> Unit
) {
    val scrollState = rememberScrollState()
    val removeIngredientUseCase = RemoveIngredientUseCase()
    Surface {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.surface)
                .padding(
                    top = com.globa.cocktails.ui.theme.Paddings.largeLarge,
                    bottom = com.globa.cocktails.ui.theme.Paddings.largeLarge,
                    start = com.globa.cocktails.ui.theme.Paddings.large,
                    end = com.globa.cocktails.ui.theme.Paddings.large
                )
                .verticalScroll(scrollState)
            ,
            horizontalAlignment = Alignment.Start
        ) {
            RedactorScreenImage(
                mode = mode,
                changeImage = changeImage,
                cocktail = cocktail
            )
            NameBlock(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = com.globa.cocktails.ui.theme.Paddings.large),
                name = cocktail.name,
                isError = isNameError,
                onNameChanged = {onItemChange(cocktail.copy(name = it))},
                mode = mode
            )
            EditableIngredientListScreen(
                modifier.padding(top = com.globa.cocktails.ui.theme.Paddings.large),
                ingredients = cocktail.ingredients,
                measures = cocktail.measures,
                isErrors = isIngredientsError,
                onIngredientsChange = {
                                      onItemChange(cocktail.copy(ingredients = it))
                },
                onMeasuresChange = {
                                   onItemChange(cocktail.copy(measures = it))
                },
                onAddButtonClick = {
                                   onItemChange(
                                       cocktail.copy(
                                           ingredients = cocktail.ingredients.plus(""),
                                           measures = cocktail.measures.plus("")
                                       )
                                   )
                },
                onRemoveButtonClick = {
                    onItemChange(
                        removeIngredientUseCase(cocktail, it)
                    )
                }
            )
            InstructionsBlock(
                modifier = Modifier.padding(top = com.globa.cocktails.ui.theme.Paddings.large),
                text = cocktail.instructions,
                isError = isInstructionError,
                onTextChanged = {onItemChange(cocktail.copy(instructions = it))}
            )
        }
    }

}

@Composable
fun RedactorScreenImage(
    modifier: Modifier = Modifier,
    mode: RedactorMode,
    changeImage: () -> Unit,
    cocktail: RecipeEditable
) {
    Text(
        text = "Photo",
        style = MaterialTheme.typography.titleMedium.plus(
            TextStyle(color = MaterialTheme.colorScheme.onSurfaceVariant)
        )
    )
    when (mode) {
        RedactorMode.EDIT-> {
            AsyncImage(
                model = cocktail.imageURL,
                contentDescription = cocktail.name,
                modifier = modifier
                    .heightIn(max = 300.dp)
                    .widthIn(max = 220.dp)
                    .clip(
                        RoundedCornerShape(
                            topStart = DPs.largeImageRound,
                            bottomStart = DPs.largeImageRound
                        )
                    )
                    .padding(top = com.globa.cocktails.ui.theme.Paddings.large)
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
                    .padding(top = com.globa.cocktails.ui.theme.Paddings.large)
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
fun NameBlock(
    modifier: Modifier = Modifier,
    name: String,
    isError: Boolean,
    onNameChanged: (String) -> Unit,
    mode: RedactorMode
) {
    OutlinedTextField(
        value = name,
        onValueChange = {onNameChanged(it)},
        modifier = modifier,
        enabled = mode == RedactorMode.ADD,
        textStyle = MaterialTheme.typography.bodyLarge,
        label = {
            Text(
                text = "Name",
                modifier = Modifier.padding(bottom = com.globa.cocktails.ui.theme.Paddings.extraSmall)
            )
        },
        isError = isError,
        shape = MaterialTheme.shapes.medium
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditableIngredientListScreen(
    modifier: Modifier = Modifier,
    ingredients: List<String>,
    measures: List<String>,
    isErrors: List<Boolean>,
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
                    .padding(top = com.globa.cocktails.ui.theme.Paddings.large)
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
                        .fillMaxWidth(0.6f),
                    label = {
                        Text(
                            text = "Ingredient $i",
                            modifier = Modifier.padding(bottom = com.globa.cocktails.ui.theme.Paddings.small)
                        )
                    },
                    singleLine = true,
                    isError = isErrors.getOrElse(i) { false },
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
                            start = com.globa.cocktails.ui.theme.Paddings.large,
                        )
                    ,
                    label = {
                        Text(
                            text = "Amount",
                            modifier = Modifier.padding(bottom = com.globa.cocktails.ui.theme.Paddings.small),
                            maxLines = 1
                        )
                    },
                    singleLine = true,
                    shape = MaterialTheme.shapes.large
                )
                IconButton(
                    onClick = { onRemoveButtonClick(i) },
                    modifier = Modifier
                        .padding(start = com.globa.cocktails.ui.theme.Paddings.large, end = 4.dp)
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
                .padding(top = com.globa.cocktails.ui.theme.Paddings.large)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InstructionsBlock(
    modifier: Modifier = Modifier,
    text: String,
    isError: Boolean,
    onTextChanged: (String) -> Unit
) {
    Column(
        modifier = modifier.background(color = MaterialTheme.colorScheme.surface),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Receipe descryption",
            modifier = Modifier.padding(top = com.globa.cocktails.ui.theme.Paddings.large),
            style = MaterialTheme.typography.titleMedium.plus(
                TextStyle(color = MaterialTheme.colorScheme.onSurfaceVariant)
            )
        )
        OutlinedTextField(
            value = text,
            onValueChange = {onTextChanged(it)},
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = com.globa.cocktails.ui.theme.Paddings.large),
            textStyle = MaterialTheme.typography.bodyLarge,
            isError = isError,
            shape = MaterialTheme.shapes.medium
        )
    }
}

@Composable
fun SaveFloatingButton(
    onClickButton: () -> Unit
) {
    FloatingActionButton(
        onClick = {onClickButton()},
        containerColor = MaterialTheme.colorScheme.secondaryContainer,
        modifier = Modifier
            .padding(
                bottom = com.globa.cocktails.ui.theme.Paddings.medium,
                end = com.globa.cocktails.ui.theme.Paddings.largeLarge
            )
            .width(92.dp)
            .height(40.dp),
        shape = MaterialTheme.shapes.extraLarge,
        elevation = FloatingActionButtonDefaults.elevation()
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_done),
            contentDescription = "Done",
            modifier = Modifier.size(15.dp),
            tint = MaterialTheme.colorScheme.onSecondaryContainer
        )
    }
}

@Composable
fun SaveDialog(
    onAccept: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            Button(
                onClick = { onAccept() }
            ) {
                Text(text = "Yes, continue")
            }
        },
        dismissButton = {
            OutlinedButton(
                onClick = { onDismiss() },
                border = BorderStroke(1.dp, color = MaterialTheme.colorScheme.onSurface)
            ) {
                Text(text = "No, return")
            }
        },
        containerColor = MaterialTheme.colorScheme.surface,
        title = {Text(text = "Finish editing?")},
        text = {Text(text = "You can return to editing at any time")}
    )
}

val testCocktail = RecipeEditable(
    id = 1,
    name = "Margarita",
    imageURL = "http://www.thecocktaildb.com/images/media/drink/wpxpvu1439905379.jpg",
    ingredients = listOf("Tequila", "Triple sec", "Lime juice", "Salt"),
    measures = listOf("1 1/2 oz", "1/2 oz", "1 oz"),
    instructions = "Rub the rim of the glass with the lime slice to make the salt stick to it. Take care to moisten only the outer rim and sprinkle the salt on it. The salt should present to the lips of the imbiber and never mix into the cocktail. Shake the other ingredients with ice, then carefully pour into the glass."
)

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun RedactorScreenPreview() {

    com.globa.cocktails.ui.theme.AppTheme {
        Surface {
            RedactorScreenBody(
                cocktail = testCocktail,
                mode = RedactorMode.ADD,
                isIngredientsError = listOf(),
                isInstructionError = false,
                isNameError = false,
                changeImage = {},
                onItemChange = {})
        }
    }
}

@Preview(widthDp = 420)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun RedactorIngredientListPreview() {
    com.globa.cocktails.ui.theme.AppTheme {
        Surface {
            EditableIngredientListScreen(
                ingredients = testCocktail.ingredients,
                measures = testCocktail.measures,
                isErrors = listOf(),
                onIngredientsChange = {},
                onMeasuresChange = {},
                onAddButtonClick = {},
                onRemoveButtonClick = {}
            )
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SaveDialogPreview() {
    com.globa.cocktails.ui.theme.AppTheme {
        Surface {
            SaveDialog({}, {})
        }
    }
}