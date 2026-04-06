package com.example.catbreeds.datasource.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "breeds")
data class BreedOverViewEntity(
    @PrimaryKey val id: String,
    val name: String,
    val imageUrl: String?
)
