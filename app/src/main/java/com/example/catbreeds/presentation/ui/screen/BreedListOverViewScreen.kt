package com.example.catbreeds.presentation.ui.screen

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.catbreeds.presentation.CatBreedsViewModel

@Composable
fun BreedListOverViewScreen(
    viewModel: CatBreedsViewModel,
    navigateToDetailScreen: (String) -> Unit,
    modifier: Modifier
) {
    val breeds = viewModel.breeds.collectAsLazyPagingItems()

    LazyColumn(modifier = modifier) {
        items(breeds.itemCount) { index ->
            breeds[index]?.let { breed ->
                Button(onClick = { navigateToDetailScreen(breed.id) }) {
                    Text(text = breed.name)
                }
            }
        }
    }
}
