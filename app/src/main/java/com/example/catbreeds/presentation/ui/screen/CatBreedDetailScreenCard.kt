package com.example.catbreeds.presentation.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.catbreeds.R
import com.example.catbreeds.datasource.entity.CatBreedEntity
import com.example.catbreeds.presentation.ui.theme.CatBreedsTheme

@Composable
fun CatBreedDetailCard(catBreed: CatBreedEntity) {
    Card() {
        Column() {
            Text(catBreed.name,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.width(8.dp))
            NameValueRowComponent(
                name = stringResource(R.string.cat_breed_detail_screen_origin_lb),
                value = catBreed.origin,
            )
            Spacer(modifier = Modifier.width(8.dp))
            NameValueRowComponent(
                name = stringResource(R.string.cat_breed_detail_screen_life_span_lb),
                value = catBreed.life_span,
            )
            Spacer(modifier = Modifier.width(8.dp))
            NameValueColumnComponent(
                name = stringResource(R.string.cat_breed_detail_screen_temperament_lb),
                value = catBreed.temperament,
            )
            Spacer(modifier = Modifier.width(8.dp))
            NameValueColumnComponent(
                name = stringResource(R.string.cat_breed_detail_screen_description_lb),
                value = catBreed.description
            )
        }
    }
}

@Composable
fun NameValueRowComponent(
    name: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = modifier) {
        Text(text = name, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = value)
    }
}

@Composable
fun NameValueColumnComponent(
    name: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = modifier) {
        Column() {
            Text(text = name, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = value)
        }
    }
}

@Preview
@Composable
fun CatBreedDetailScreenPreview() {
    CatBreedsTheme() {
        CatBreedDetailCard(
            catBreed = CatBreedEntity(
                id = "1",
                name = "Abyssinian",
                origin = "Egypt",
                temperament = "Active, Energetic, Independent, Intelligent, Gentle",
                description = "The Abyssinian is easy to care for and active cat who is a great purr-owner!",
                life_span = "14 - 15",
                image = null
            )
        )
    }
}
