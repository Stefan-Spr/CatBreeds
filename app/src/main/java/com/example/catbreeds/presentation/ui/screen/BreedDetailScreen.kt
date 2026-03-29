package com.example.catbreeds.presentation.ui.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier


@Composable
fun BreedDetailScreen(
    onBackClick: () -> Unit,
    modifier: Modifier) {
    var backHandlingEnabled by remember { mutableStateOf(true) }
    BackHandler(backHandlingEnabled) {
        onBackClick()
    }
    Column( modifier = modifier
    ) {
        Text("DetailScreen")
    }
}
