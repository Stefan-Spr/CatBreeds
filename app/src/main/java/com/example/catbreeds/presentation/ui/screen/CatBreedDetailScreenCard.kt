package com.example.catbreeds.presentation.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.catbreeds.R
import com.example.catbreeds.datasource.entity.CatImageEntity
import com.example.catbreeds.presentation.ui.states.CatBreedDetailState
import com.example.catbreeds.presentation.ui.theme.CatBreedsTheme
import kotlinx.coroutines.flow.flowOf

@Composable
fun CatBreedDetailCard(catBreed: CatBreedDetailState,
                       images: LazyPagingItems<CatImageEntity>
) {
    Card() {
        Column() {
            Text(catBreed.name,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.width(8.dp))
            CatBreedImagesPager(images)
            Spacer(modifier = Modifier.width(8.dp))
            NameValueRowComponent(
                name = stringResource(R.string.cat_breed_detail_screen_origin_lb),
                value = catBreed.origin,
            )
            Spacer(modifier = Modifier.width(8.dp))
            NameValueRowComponent(
                name = stringResource(R.string.cat_breed_detail_screen_life_span_lb),
                value = catBreed.lifeSpan,
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
fun CatBreedImagesPager(
    images: LazyPagingItems<CatImageEntity>,
    modifier: Modifier = Modifier,
) {
    val pagerState = rememberPagerState(
        pageCount = { images.itemCount }
    )

    Box(modifier = modifier) {
        if (images.itemCount == 0 && images.loadState.refresh !is LoadState.Loading) {
            ImagePlaceholder(text = "No images available")
        } else {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center
            ) {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .height(250.dp)
                ) { page ->
                    val image = images[page]
                    if (image != null) {
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(image.url)
                                    .build(),
                                contentDescription = null,
                                contentScale = ContentScale.FillHeight,
                                modifier = Modifier.fillMaxHeight(),
                                //error = painterResource(R.drawable.ic_broken_image)
                            )
                        }
                    } else {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }

                if (images.itemCount > 1) {
                    PageIndicator(images.itemCount, pagerState)
                }
            }
        }
    }
}

@Composable
private fun PageIndicator(pageCount: Int, pagerState: PagerState) {
    Row(
        Modifier
            .height(24.dp)
            .fillMaxWidth()
            .padding(top = 8.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(pageCount) { iteration ->
            val color = if (pagerState.currentPage == iteration)
                MaterialTheme.colorScheme.primary
            else
                MaterialTheme.colorScheme.outlineVariant

            Box(
                modifier = Modifier
                    .padding(2.dp)
                    .clip(CircleShape)
                    .background(color)
                    .size(8.dp)
            )
        }
    }
}
@Composable
private fun ImagePlaceholder(text: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant),
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, style = MaterialTheme.typography.bodyMedium)
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

    val pagingFlow = remember {
        flowOf(
            PagingData.from(
                listOf(
                    CatImageEntity("1", "https://cdn2.thecatapi.com/images/1.jpg"),
                    CatImageEntity("2", "https://cdn2.thecatapi.com/images/2.jpg")
                )
            )
        )
    }

    val images = pagingFlow.collectAsLazyPagingItems()

    CatBreedsTheme() {
        CatBreedDetailCard(
            catBreed = CatBreedDetailState(
                name = "Abyssinian",
                origin = "Egypt",
                temperament = "Active, Energetic, Independent, Intelligent, Gentle",
                description = "The Abyssinian is easy to care for and active cat who is a great purr-owner!",
                lifeSpan = "14 - 15",
            ),
            images = images
        )
    }
}
