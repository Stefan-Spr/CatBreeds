package com.example.catbreeds.datasource

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.catbreeds.datasource.api.CatApiService
import com.example.catbreeds.datasource.entity.CatBreedOverViewEntity
import com.example.catbreeds.datasource.entity.CatImageEntity
import com.example.catbreeds.datasource.paging.BreedsPagingSource
import com.example.catbreeds.datasource.paging.ImagesPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CatRepository @Inject constructor(
    private val api: CatApiService
) {
    fun getBreedsPager(): Flow<PagingData<CatBreedOverViewEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { BreedsPagingSource(api) }
        ).flow
    }

    suspend fun getBreedDetails(breedId: String) = api.getBreedById(breedId)

    fun getImagesPager(breedId: String): Flow<PagingData<CatImageEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10
            ),
            pagingSourceFactory = {
                ImagesPagingSource(api, breedId)
            }
        ).flow
    }
}
