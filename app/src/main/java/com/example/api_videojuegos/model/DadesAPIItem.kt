// kotlin
package com.example.api_videojuegos.model

data class DadesAPIItem(
    val id: Int,
    val nombre: String,
    val imagenCaratula: String?,
    val descripcion: String? = null,
    val genero: String? = null,
    val plataforma: String? = null,
    val publisher: String? = null,
    val releaseDate: String? = null
)
