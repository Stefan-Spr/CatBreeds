package com.example.catbreeds.presentation.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.catbreeds.R
import com.example.catbreeds.presentation.CatBreedsViewModel

@Composable
fun BreedListOverViewScreen(
    viewModel: CatBreedsViewModel,
    navigateToDetailScreen: (String) -> Unit,
    modifier: Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        HeaderComponent(text = stringResource(R.string.cat_breeds_over_view_screen_title_lb))
        val breeds = viewModel.breeds.collectAsLazyPagingItems()
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(breeds.itemCount) { index ->
                breeds[index]?.let { breed ->
                    Button(onClick = { navigateToDetailScreen(breed.id) }) {
                        Text(text = breed.name)
                    }
                }
            }
        }
    }
}
