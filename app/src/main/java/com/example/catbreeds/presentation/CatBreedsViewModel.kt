package com.example.catbreeds.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.catbreeds.datasource.CatRepository
import com.example.catbreeds.datasource.entity.ImageSearchFilter
import com.example.catbreeds.presentation.ui.mapper.mapToState
import com.example.catbreeds.presentation.ui.states.CatBreedDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatBreedsViewModel @Inject constructor(
    private val repository: CatRepository
) : ViewModel() {

    private val lCatBreedDetailState = MutableStateFlow<CatBreedDetailState?>(null)
    val catBreedDetailState: StateFlow<CatBreedDetailState?> = lCatBreedDetailState

    private val lFilter = MutableStateFlow(ImageSearchFilter())
    val filter = lFilter.asStateFlow()

    val breeds = repository.getBreedsPager()
        .cachedIn(viewModelScope)

    fun getBreedDetails(breedId: String){
        viewModelScope.launch {
            lCatBreedDetailState.value = repository.getBreedDetails(breedId).mapToState()
        }
    }

    fun getImages(breedId: String) =
        repository.getImagesPager(breedId)
            .cachedIn(viewModelScope)

    @OptIn(ExperimentalCoroutinesApi::class)
    val images = filter
        .flatMapLatest { filter ->
            repository.searchImages(filter)
        }
        .cachedIn(viewModelScope)

    fun updateFilter(newFilter: ImageSearchFilter) {
        lFilter.value = newFilter
    }

}
