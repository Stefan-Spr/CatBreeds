package com.example.catbreeds.datasource.api

import com.example.catbreeds.datasource.entity.CatBreedEntity
import com.example.catbreeds.datasource.entity.CatBreedOverViewEntity
import com.example.catbreeds.datasource.entity.CatImageEntity
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CatApiService {
    @GET("breeds")
    suspend fun getBreeds(
    ): List<CatBreedOverViewEntity>

    @GET("breeds/{breed_id}")
    suspend fun getBreedById(
        @Path("breed_id") breedId: String
    ): CatBreedEntity

    @Suppress("LongParameterList")
    @GET("images/search")
    suspend fun searchImages(
        @Query("breed_ids") breedId: String? = null,
        @Query("category_ids") categoryId: Int? = null,
        @Query("mime_types") mimeTypes: String? = null,
        @Query("order") order: String? = null,
        @Query("limit") limit: Int,
        @Query("page") page: Int
    ): List<CatImageEntity>
}
