package com.example.catbreeds.presentation.ui.mapper

import com.example.catbreeds.datasource.entity.BreedOverViewEntity
import com.example.catbreeds.presentation.ui.states.CatBreedOverViewState

fun BreedOverViewEntity.mapToState(): CatBreedOverViewState {
    return CatBreedOverViewState(
        id = id,
        name = name,
        imageUrl = imageUrl
    )
}
