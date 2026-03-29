package com.example.catbreeds.datasource.entity

@Suppress("ConstructorParameterNaming")
data class CatBreedEntity(
    val id: String,
    val name: String,
    val origin: String,
    val temperament: String,
    val description: String,
    val life_span: String,
    val image: CatImageEntity?,
)
