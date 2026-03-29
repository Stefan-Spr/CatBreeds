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
        @Query("limit") limit: Int,
        @Query("page") page: Int
    ): List<CatBreedOverViewEntity>

    @GET("breeds/{breed_id}")
    suspend fun getBreedById(
        @Path("breed_id") breedId: String
    ): CatBreedEntity

    @GET("images/{referenceId}")
    suspend fun getImageByID(
        @Path("referenceId") id: String
    ): CatImageEntity


    @GET("images/search")
    suspend fun getImagesByBreed(
        @Query("breed_ids") breedId: String,
        @Query("limit") limit: Int,
        @Query("page") page: Int
    ): List<CatImageEntity>
}
