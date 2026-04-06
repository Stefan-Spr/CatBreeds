package com.example.catbreeds.datasource.entity

data class ImageSearchFilter(
    val breedId: String? = null,
    val categoryId: Int? = null,
    val mimeType: String? = null,
    val order: String? = "DESC"
)
