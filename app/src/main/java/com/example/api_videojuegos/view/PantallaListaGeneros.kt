package com.example.api_videojuegos.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.api_videojuegos.model.DadesAPIItem
import com.example.api_videojuegos.viewmodel.VideojuegoViewModel

@Composable
fun PantallaListaGeneros(
    viewModel: VideojuegoViewModel = viewModel(),
    onGeneroClick: (DadesAPIItem) -> Unit
) {
    val listaVideojuegos by viewModel.videojuegos.observeAsState(emptyList())
    val loading by viewModel.loading.observeAsState(false)
    val error by viewModel.error.observeAsState(null)

    LaunchedEffect(Unit) {
        viewModel.cargarVideojuegos()
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        when {
            loading -> {
                CircularProgressIndicator()
            }
            error != null -> {
                Text(text = "Error: ${error}", color = MaterialTheme.colorScheme.error)
            }
            listaVideojuegos.isEmpty() -> {
                Text(text = "No hay videojuegos disponibles", modifier = Modifier.padding(16.dp))
            }
            else -> {
                LazyColumn(modifier = Modifier.fillMaxSize().padding(8.dp)) {
                    items(listaVideojuegos) { videojuego ->
                        ItemGeneroVideojuego(videojuego) {
                            onGeneroClick(videojuego)
                        }
                    }
                }
            }
        }
    }
}
