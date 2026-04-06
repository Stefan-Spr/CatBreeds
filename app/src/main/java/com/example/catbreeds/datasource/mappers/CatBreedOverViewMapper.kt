package com.example.catbreeds.datasource.mappers

import com.example.catbreeds.datasource.entity.BreedOverViewEntity
import com.example.catbreeds.datasource.entity.CatBreedOverViewEntity

fun CatBreedOverViewEntity.toEntity(): BreedOverViewEntity {
    return BreedOverViewEntity(
        id = id,
        name = name,
        imageUrl = image?.url
    )
}
