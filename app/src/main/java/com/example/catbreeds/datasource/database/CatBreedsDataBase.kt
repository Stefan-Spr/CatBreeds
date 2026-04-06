package com.example.catbreeds.datasource.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.catbreeds.datasource.entity.BreedOverViewEntity

@Database(
    entities = [
        BreedOverViewEntity::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class CatBreedsDataBase : RoomDatabase() {
    abstract fun breedDao(): BreedDao
}
