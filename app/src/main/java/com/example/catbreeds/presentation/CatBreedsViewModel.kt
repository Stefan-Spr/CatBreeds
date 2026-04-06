package com.example.catbreeds.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.catbreeds.datasource.CatRepository
import com.example.catbreeds.datasource.entity.ImageSearchFilter
import com.example.catbreeds.presentation.ui.mapper.mapToState
import com.example.catbreeds.presentation.ui.states.CatBreedDetailState
import com.example.catbreeds.presentation.ui.states.CatBreedOverViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatBreedsViewModel @Inject constructor(
    private val repository: CatRepository
) : ViewModel() {

    private val lCatBreedDetailState = MutableStateFlow<CatBreedDetailState?>(null)
    val catBreedDetailState: StateFlow<CatBreedDetailState?> = lCatBreedDetailState

    private val lQuery = MutableStateFlow("")
    val query = lQuery.asStateFlow()

    private val numberSuggestions: Int = 5

    fun getBreedDetails(breedId: String){
        viewModelScope.launch {
            lCatBreedDetailState.value = repository.getBreedDetails(breedId).mapToState()
        }
    }

    fun getImages(breedId: String) =
        repository.getImagesPager(breedId)
            .cachedIn(viewModelScope)

    @OptIn(ExperimentalCoroutinesApi::class)
    val images = query
        .flatMapLatest { query ->
            val breed = breeds.value.find {
                it.name.equals(query, ignoreCase = true)
            }

            repository.searchImages(
                ImageSearchFilter(breedId = breed?.id)
            )
        }
        .cachedIn(viewModelScope)

    fun onQueryChanged(text: String) {
        lQuery.value = text
    }

    fun onSuggestionClicked(breed: CatBreedOverViewState) {
        lQuery.value = breed.name
    }

    val breeds = repository.getBreeds().map { list -> list.map { it.mapToState() } }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())


    val suggestions = combine(lQuery, breeds) { query, breeds ->
        if (query.isBlank()) emptyList()
        else breeds.filter {
            it.name.contains(query, ignoreCase = true)
        }.take(numberSuggestions)
    }

    init {
        viewModelScope.launch {
            repository.refreshBreeds()
        }
    }

}
