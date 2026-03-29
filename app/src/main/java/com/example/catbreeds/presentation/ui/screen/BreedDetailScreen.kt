package com.example.catbreeds.presentation.ui.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.catbreeds.R
import com.example.catbreeds.presentation.CatBreedsViewModel


@Composable
fun BreedDetailScreen(
    breedId: String,
    viewModel: CatBreedsViewModel,
    onBackClick: () -> Unit,
    modifier: Modifier) {
    var backHandlingEnabled by remember { mutableStateOf(true) }
    BackHandler(backHandlingEnabled) {
        onBackClick()
    }

    val catBreedState by viewModel.catBreedDetailState.collectAsState()

    LaunchedEffect(breedId) {
        viewModel.getBreedDetails(breedId)
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        catBreedState?.let { catBreed ->
            CatBreedDetailCard(catBreed)
        } ?: Text(stringResource(R.string.cat_breed_detail_screen_error_lb))
    }
}
