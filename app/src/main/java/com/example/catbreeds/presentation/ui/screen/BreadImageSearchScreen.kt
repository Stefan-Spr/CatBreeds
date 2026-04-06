package com.example.catbreeds.presentation.ui.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.example.catbreeds.R
import com.example.catbreeds.datasource.entity.CatImageEntity
import com.example.catbreeds.datasource.entity.ImageSearchFilter
import com.example.catbreeds.presentation.CatBreedsViewModel
import com.example.catbreeds.presentation.ui.theme.CatBreedsTheme
import kotlinx.coroutines.flow.flowOf

@Composable
fun BreedImageSearchScreen(
    viewModel: CatBreedsViewModel,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
){
    val images = viewModel.images.collectAsLazyPagingItems()
    BreedImageSearchContent(
        images = images,
        onSearch = { breedId ->
            viewModel.updateFilter(ImageSearchFilter(breedId = breedId))
        },
        onBackClick = onBackClick,
        modifier = modifier
    )
}

@Composable
fun BreedImageSearchContent(
    images: LazyPagingItems<CatImageEntity>,
    onSearch: (String) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var backHandlingEnabled by remember { mutableStateOf(true) }
    BackHandler(backHandlingEnabled) {
        onBackClick()
    }

    Column (modifier = modifier) {
        SearchBar(onSearch = onSearch)
        ImageGrid(images)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(onSearch: (String) -> Unit) {
    var text by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedTextField(
        value = text,
        onValueChange = {
            text = it
            onSearch(it)
        },
        label = { Text(stringResource(R.string.cat_breed_search_screen_input_lb)) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .focusRequester(focusRequester),
        shape = CircleShape,
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearch(text)
                keyboardController?.hide()
            }
        ),
        trailingIcon = {
            IconButton(onClick = {
                onSearch(text)
                keyboardController?.hide()
            }) {
                Icon(painterResource(R.drawable.search), null)
            }
        }
    )
}

@Composable
fun ImageGrid(images: LazyPagingItems<CatImageEntity>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2)
    ) {
        items(images.itemCount) { index ->
            val image = images[index]

            if (image != null) {
                AsyncImage(
                    model = image.url,
                    contentDescription = null,
                    modifier = Modifier
                        .aspectRatio(1f)
                        .padding(4.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BreedImageSearchScreenPreview() {
    val pagingFlow = flowOf(
        PagingData.from(
            listOf(
                CatImageEntity("1", "https://cdn2.thecatapi.com/images/0XYvRd7oD.jpg"),
                CatImageEntity("2", "https://cdn2.thecatapi.com/images/1.jpg"),
                CatImageEntity("3", "https://cdn2.thecatapi.com/images/2.jpg"),
                CatImageEntity("4", "https://cdn2.thecatapi.com/images/3.jpg")
            )
        )
    )
    val images = pagingFlow.collectAsLazyPagingItems()

    CatBreedsTheme {
        BreedImageSearchContent(
            images = images,
            onSearch = {},
            onBackClick = {}
        )
    }
}
