package com.example.api_videojuegos.model

// DTO para un juego dentro de la respuesta de RAWG
data class RawgGame(
    val id: Int? = null,
    val name: String? = null,
    val background_image: String? = null
)
