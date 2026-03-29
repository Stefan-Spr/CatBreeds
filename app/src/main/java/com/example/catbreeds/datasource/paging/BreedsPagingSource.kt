package com.example.catbreeds.datasource.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.catbreeds.datasource.api.CatApiService
import com.example.catbreeds.datasource.entity.CatBreedOverViewEntity

class BreedsPagingSource(
    private val api: CatApiService
) : PagingSource<Int, CatBreedOverViewEntity>() {

    @Suppress("TooGenericExceptionCaught")
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CatBreedOverViewEntity> {
        return try {
            val page = params.key ?: 0
            val limit = params.loadSize

            val breeds = api.getBreeds(
                limit = limit,
                page = page
            )

            LoadResult.Page(
                data = breeds,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (breeds.isEmpty()) null else page + 1
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, CatBreedOverViewEntity>): Int? {
        return state.anchorPosition?.let { position ->
            val page = state.closestPageToPosition(position)
            page?.prevKey?.plus(1) ?: page?.nextKey?.minus(1)
        }
    }
}
