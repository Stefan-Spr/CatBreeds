package com.example.catbreeds.presentation.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.catbreeds.R
import com.example.catbreeds.presentation.CatBreedsViewModel

@Composable
fun BreedListOverViewScreen(
    viewModel: CatBreedsViewModel,
    navigateToDetailScreen: (String) -> Unit,
    navigateToSearchScreen: () -> Unit,
    modifier: Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.testTag(stringResource(R.string.test_tag_breed_list))
    ) {
        CatBreedOverViewHeader(
            text = stringResource(R.string.cat_breeds_over_view_screen_title_lb),
            navigateToSearchScreen = navigateToSearchScreen
        )
        val breeds = viewModel.breeds.collectAsStateWithLifecycle()
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(breeds.value.size) { index ->
                BreedOverViewComponent(
                    breed = breeds.value[index],
                    onBreedClick = navigateToDetailScreen
                )
            }
        }
    }
}
