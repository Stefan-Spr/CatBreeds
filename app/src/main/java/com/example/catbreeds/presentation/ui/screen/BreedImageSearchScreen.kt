package com.example.catbreeds.presentation.ui.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.example.catbreeds.R
import com.example.catbreeds.datasource.entity.CatImageEntity
import com.example.catbreeds.presentation.CatBreedsViewModel
import com.example.catbreeds.presentation.ui.states.CatBreedOverViewState
import com.example.catbreeds.presentation.ui.theme.CatBreedsTheme
import kotlinx.coroutines.flow.flowOf

@Composable
fun BreedImageSearchScreen(
    viewModel: CatBreedsViewModel,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
){
    val query by viewModel.query.collectAsState()
    val suggestions by viewModel.suggestions.collectAsState(emptyList())
    val images = viewModel.images.collectAsLazyPagingItems()
    BreedImageSearchContent(
        query = query,
        images = images,
        suggestions = suggestions,
        onQueryChange = viewModel::onQueryChanged,
        onSuggestionClick = viewModel::onSuggestionClicked,
        onBackClick = onBackClick,
        modifier = modifier
    )
}

@Suppress("LongParameterList")
@Composable
fun BreedImageSearchContent(
    query: String,
    images: LazyPagingItems<CatImageEntity>,
    suggestions: List<CatBreedOverViewState>,
    onQueryChange: (String) -> Unit,
    onSuggestionClick: (CatBreedOverViewState) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var backHandlingEnabled by remember { mutableStateOf(true) }
    BackHandler(backHandlingEnabled) {
        onBackClick()
    }

    Column (modifier = modifier) {
        CatSearchBar(
            query = query,
            suggestions = suggestions,
            onQueryChange = onQueryChange,
            onSuggestionClick = onSuggestionClick
        )
        ImageGrid(images)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatSearchBar(
    query: String,
    suggestions: List<CatBreedOverViewState>,
    onQueryChange: (String) -> Unit,
    onSuggestionClick: (CatBreedOverViewState) -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }

    SearchBar(
        inputField = {
            SearchBarDefaults.InputField(
                query = query,
                onQueryChange = onQueryChange,
                onSearch = { isExpanded = false },
                expanded = isExpanded,
                onExpandedChange = { isExpanded = it },
                placeholder = { Text(stringResource(R.string.cat_breed_search_screen_input_lb)) },
                leadingIcon = {
                    Icon(
                        painterResource(R.drawable.search),
                        contentDescription = null,
                        modifier = Modifier.size(25.dp)
                    )
                }
            )
        },
        expanded = isExpanded,
        onExpandedChange = { isExpanded = it },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        LazyColumn {
            items(suggestions) { breed ->
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clickable {
                        onSuggestionClick(breed)
                        isExpanded = false
                    }) {
                    Text(text = breed.name)
                }
            }
        }
    }
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
            query = "",
            images = images,
            suggestions = emptyList(),
            onQueryChange = {},
            onSuggestionClick = {},
            onBackClick = {}
        )
    }
}
