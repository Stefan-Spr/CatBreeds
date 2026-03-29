package com.example.catbreeds.presentation.ui.mapper

import com.example.catbreeds.datasource.entity.CatBreedEntity
import com.example.catbreeds.presentation.ui.states.CatBreedDetailState


fun CatBreedEntity.mapToState(): CatBreedDetailState {
    return CatBreedDetailState(
        name = name,
        description = description,
        temperament = temperament,
        origin = origin,
        lifeSpan = life_span,
    )
}
