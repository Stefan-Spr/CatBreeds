package com.example.catbreeds.presentation.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.catbreeds.R
import com.example.catbreeds.presentation.ui.states.CatBreedOverViewState

@Composable
fun BreedOverViewComponent(
    breed: CatBreedOverViewState,
    onBreedClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val cardTag = stringResource(R.string.test_tag_breed_card_prefix) + breed.id
    Card(
        onClick = { onBreedClick(breed.id) },
        modifier = modifier
            .width(300.dp)
            .testTag(cardTag),
    ) {
        Column {
            Text(
                text = breed.name,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(12.dp).fillMaxWidth()
            )

            AsyncImage(
                model = breed.imageUrl,
                contentDescription = "Image of ${breed.name}",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}
