package com.example.catbreeds.datasource.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.catbreeds.datasource.entity.BreedOverViewEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BreedDao {
    @Query("SELECT * FROM breeds ORDER BY name ASC")
    fun getBreeds(): Flow<List<BreedOverViewEntity>>

    @Query("SELECT * FROM breeds WHERE id = :id")
    suspend fun getBreedById(id: String): BreedOverViewEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(breeds: List<BreedOverViewEntity>)

    @Query("DELETE FROM breeds")
    suspend fun clearAll()
}
