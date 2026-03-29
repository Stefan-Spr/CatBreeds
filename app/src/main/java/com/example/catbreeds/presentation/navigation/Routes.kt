package com.example.catbreeds.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
object CatBreedsOverViewRoute

@Serializable
data class CatBreedDetailsRoute(val id: String)
