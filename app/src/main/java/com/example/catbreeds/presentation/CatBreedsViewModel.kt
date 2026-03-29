package com.example.catbreeds.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.catbreeds.datasource.CatRepository
import com.example.catbreeds.datasource.entity.CatBreedEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatBreedsViewModel @Inject constructor(
    private val repository: CatRepository
) : ViewModel() {

    private val _catBreedDetailState = MutableStateFlow<CatBreedEntity?>(null)
    val catBreedDetailState: StateFlow<CatBreedEntity?> = _catBreedDetailState

    val breeds = repository.getBreedsPager()
        .cachedIn(viewModelScope)

    fun getBreedDetails(breedId: String){
        viewModelScope.launch {
            _catBreedDetailState.value = repository.getBreedDetails(breedId)
        }
    }

    fun getImages(breedId: String) =
        repository.getImagesPager(breedId)
            .cachedIn(viewModelScope)

}
