package com.example.catbreeds.datasource

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.catbreeds.datasource.api.CatApiService
import com.example.catbreeds.datasource.database.BreedDao
import com.example.catbreeds.datasource.entity.BreedOverViewEntity
import com.example.catbreeds.datasource.entity.CatImageEntity
import com.example.catbreeds.datasource.entity.ImageSearchFilter
import com.example.catbreeds.datasource.mappers.toEntity
import com.example.catbreeds.datasource.paging.ImagesPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CatRepository @Inject constructor(
    private val api: CatApiService,
    private val breedDao: BreedDao
) {

    fun getBreeds(): Flow<List<BreedOverViewEntity>> {
        return breedDao.getBreeds()
    }

    suspend fun refreshBreeds() {
        val breeds = api.getBreeds()
        breedDao.clearAll()
        breedDao.insertAll(breeds.map { it.toEntity() })
    }

    suspend fun getBreedDetails(breedId: String) = api.getBreedById(breedId)

    fun getImagesPager(breedId: String): Flow<PagingData<CatImageEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10
            ),
            pagingSourceFactory = {
                ImagesPagingSource(api, ImageSearchFilter(breedId = breedId))
            }
        ).flow
    }

    fun searchImages(filter: ImageSearchFilter): Flow<PagingData<CatImageEntity>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = {
                ImagesPagingSource(api, filter)
            }
        ).flow
    }
}
