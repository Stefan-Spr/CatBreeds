package com.example.catbreeds.datasource.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.catbreeds.datasource.api.CatApiService
import com.example.catbreeds.datasource.entity.CatImageEntity
import com.example.catbreeds.datasource.entity.ImageSearchFilter

class ImagesPagingSource(
    private val api: CatApiService,
    private val filter: ImageSearchFilter
) : PagingSource<Int, CatImageEntity>() {

    @Suppress("TooGenericExceptionCaught")
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CatImageEntity> {
        return try {
            val page = params.key ?: 0

            val images = api.searchImages(
                breedId = filter.breedId,
                categoryId = filter.categoryId,
                mimeTypes = filter.mimeType,
                order = filter.order,
                limit = params.loadSize,
                page = page
            )

            LoadResult.Page(
                data = images,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (images.isEmpty()) null else page + 1
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, CatImageEntity>): Int? {
        return state.anchorPosition?.let { position ->
            val page = state.closestPageToPosition(position)
            page?.prevKey?.plus(1) ?: page?.nextKey?.minus(1)
        }
    }
}
