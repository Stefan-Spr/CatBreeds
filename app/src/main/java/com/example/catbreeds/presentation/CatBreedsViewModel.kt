package com.example.catbreeds.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.catbreeds.datasource.CatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CatBreedsViewModel @Inject constructor(
    private val repository: CatRepository
) : ViewModel() {

    val breeds = repository.getBreedsPager()
        .cachedIn(viewModelScope)

    fun getImages(breedId: String) =
        repository.getImagesPager(breedId)
            .cachedIn(viewModelScope)

}
