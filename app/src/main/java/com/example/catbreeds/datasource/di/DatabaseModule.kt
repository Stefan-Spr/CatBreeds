package com.example.catbreeds.datasource.di

import android.content.Context
import androidx.room.Room
import com.example.catbreeds.datasource.database.BreedDao
import com.example.catbreeds.datasource.database.CatBreedsDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): CatBreedsDataBase {
        return Room.databaseBuilder(
            context,
            CatBreedsDataBase::class.java,
            "cat_database"
        ).build()
    }

    @Provides
    fun provideBreedDao(db: CatBreedsDataBase): BreedDao = db.breedDao()
}
