package com.example.api_videojuegos.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.api_videojuegos.data.VideojuegoEntity
import com.example.api_videojuegos.model.DadesAPIItem
import com.example.api_videojuegos.viewmodel.VideojuegoDetalleViewModel
import kotlinx.coroutines.launch

@Composable
fun PantallaDetalleJuego(
    videojuego: DadesAPIItem,
    viewModel: VideojuegoDetalleViewModel = viewModel()
) {
    val scope = rememberCoroutineScope()
    var yaGuardado by remember { mutableStateOf(false) }

    LaunchedEffect(videojuego) {
        yaGuardado = viewModel.comprobarGuardado(videojuego.id)
    }

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val maxW = maxWidth
        val isSmall = maxW < 600.dp
        val isMedium = maxW in 600.dp..840.dp

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                if (!isSmall) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        AsyncImage(
                            model = videojuego.imagenCaratula,
                            contentDescription = videojuego.nombre,
                            modifier = Modifier
                                .size(if (isMedium) 220.dp else 300.dp)
                                .padding(8.dp),
                            contentScale = ContentScale.Crop
                        )
                        Column(
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(text = videojuego.nombre, style = MaterialTheme.typography.headlineSmall)
                            Text(text = "Género: Desconocido")
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = "Sin descripción", style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                } else {
                    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                        AsyncImage(
                            model = videojuego.imagenCaratula,
                            contentDescription = videojuego.nombre,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(220.dp),
                            contentScale = ContentScale.Crop
                        )
                        Column(modifier = Modifier.padding(8.dp)) {
                            Text(text = videojuego.nombre, style = MaterialTheme.typography.headlineSmall)
                            Text(text = "Género: Desconocido")
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = "Sin descripción", style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                scope.launch {
                    val entity = VideojuegoEntity(
                        id = videojuego.id,
                        nombre = videojuego.nombre,
                        genero = null,
                        descripcion = null,
                        thumbnail = videojuego.imagenCaratula
                    )
                    viewModel.guardar(entity)
                    yaGuardado = true
                }
            }, enabled = !yaGuardado) {
                Text(if (yaGuardado) "Guardado" else "Guardar en favoritos")
            }
        }
    }
}
