package com.example.api_videojuegos.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.unit.dp
import com.example.api_videojuegos.model.DadesAPIItem
import com.example.api_videojuegos.viewmodel.VideojuegoViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search

@Composable
fun PantallaListaGeneros(
    viewModel: VideojuegoViewModel = viewModel(),
    onGeneroClick: (DadesAPIItem) -> Unit
) {
    val isPreview = LocalInspectionMode.current

    var query by remember { mutableStateOf("") }

    // 🔥 OBSERVAMOS LOS ESTADOS REALES DEL VIEWMODEL
    val listaVideojuegos by viewModel.videojuegos.observeAsState(emptyList())
    val loading by viewModel.loading.observeAsState(false)
    val error by viewModel.error.observeAsState(null)

    // Llamar a la API al iniciar
    LaunchedEffect(Unit) {
        if (!isPreview) {
            viewModel.cargarVideojuegos()
        }
    }

    val listaFiltrada = listaVideojuegos.filter {
        it.nombre.contains(query, ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {

        TextField(
            value = query,
            onValueChange = { query = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Buscar juego...") },
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = "Buscar")
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        when {
            loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            error != null -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Error: $error",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }

            listaFiltrada.isEmpty() -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No hay videojuegos disponibles")
                }
            }

            else -> {
                LazyColumn {
                    items(listaFiltrada) { videojuego ->
                        ItemGeneroVideojuego(videojuego) {
                            onGeneroClick(videojuego)
                        }
                    }
                }
            }
        }
    }
}
