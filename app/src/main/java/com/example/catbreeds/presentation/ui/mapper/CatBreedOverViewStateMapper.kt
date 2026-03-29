package com.example.catbreeds.presentation.ui.mapper

import com.example.catbreeds.datasource.entity.CatBreedOverViewEntity
import com.example.catbreeds.presentation.ui.states.CatBreedOverViewState

fun  CatBreedOverViewEntity.mapToState(): CatBreedOverViewState {
    return CatBreedOverViewState(
        id = id,
        name = name,
        imageUrl = image?.url
    )
}
