package com.example.api_videojuegos.view

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.api_videojuegos.model.DadesAPIItem
import com.example.api_videojuegos.viewmodel.VideojuegoViewModel

@Composable
fun PantallaListaGeneros(
    viewModel: VideojuegoViewModel = viewModel(),
    onGeneroClick: (DadesAPIItem) -> Unit
) {
    val listaVideojuegos by viewModel.videojuegos.observeAsState(emptyList())

    LaunchedEffect(Unit) {
        viewModel.cargarVideojuegos()
    }

    LazyColumn {
        items(listaVideojuegos) { videojuego ->
            ItemGeneroVideojuego(videojuego) {
                onGeneroClick(videojuego)
            }
        }
    }
}
