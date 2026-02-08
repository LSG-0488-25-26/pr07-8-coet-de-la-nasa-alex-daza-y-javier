package com.example.api_videojuegos.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "videojuegos")
data class VideojuegoEntity(
    @PrimaryKey val id: Int,
    val nombre: String,
    val genero: String? = null,
    val descripcion: String? = null,
    val thumbnail: String? = null
)
