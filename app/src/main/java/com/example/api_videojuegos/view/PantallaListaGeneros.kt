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

    // Estados locales de la pantalla
    var query by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }

    // Lista de videojuegos desde el ViewModel o falsa en Preview
    val listaVideojuegos by viewModel.videojuegos.observeAsState(
        if (isPreview) {
            listOf(
                DadesAPIItem(1, "Fortnite", ""),
                DadesAPIItem(2, "Warframe", ""),
                DadesAPIItem(3, "Apex Legends", "")
            )
        } else emptyList()
    )

    // Llamada a la API SOLO cuando se ejecuta la app real
    LaunchedEffect(Unit) {
        if (!isPreview) {
            try {
                loading = true
                viewModel.cargarVideojuegos()
            } catch (e: Exception) {
                error = e.message
            } finally {
                loading = false
            }
        }
    }

    // Filtrado por texto de búsqueda
    val listaFiltrada = listaVideojuegos.filter {
        it.nombre.contains(query, ignoreCase = true)
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
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
                        Text(
                            text = "No hay videojuegos disponibles",
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
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
}
