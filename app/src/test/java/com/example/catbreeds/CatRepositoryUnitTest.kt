package com.example.catbreeds

import com.example.catbreeds.datasource.CatRepository
import com.example.catbreeds.datasource.api.CatApiService
import com.example.catbreeds.datasource.entity.CatBreedEntity
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class CatRepositoryUnitTest {

    @Mock
    private lateinit var apiService: CatApiService

    private lateinit var repository: CatRepository

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        repository = CatRepository(apiService)
    }

    @Test
    fun `getBreedDetails returns correct breed data`() = runTest {
        val breedId = "abys"
        val expectedBreed = CatBreedEntity(
            id = breedId,
            name = "Abyssinian",
            origin = "Egypt",
            temperament = "Active",
            description = "Description",
            life_span = "12-15"
        )
        `when`(apiService.getBreedById(breedId)).thenReturn(expectedBreed)

        val result = repository.getBreedDetails(breedId)

        assertEquals(expectedBreed, result)
    }
}
