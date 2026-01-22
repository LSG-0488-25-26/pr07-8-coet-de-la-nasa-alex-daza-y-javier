package com.example.lazycomponents.model

import androidx.compose.ui.graphics.Color

data class GeneroVideojuego(
    val nombreJuego: String,
    val genero: String,
    val imagenRes: Int,
    val descripcion: String,
    val colorBorde: Color
)
