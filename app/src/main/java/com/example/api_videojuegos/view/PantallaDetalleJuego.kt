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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun PantallaDetalleJuego(
    videojuego: DadesAPIItem,
    viewModel: VideojuegoDetalleViewModel = viewModel<VideojuegoDetalleViewModel>()
) {
    val scope = rememberCoroutineScope()
    var yaGuardado by remember { mutableStateOf(false) }

    LaunchedEffect(videojuego) {
        yaGuardado = viewModel.comprobarGuardado(videojuego.id)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                AsyncImage(
                    model = videojuego.imagenCaratula,
                    contentDescription = videojuego.nombre,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(240.dp),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(12.dp))

                Column(modifier = Modifier.padding(12.dp)) {
                    Text(text = videojuego.nombre, style = MaterialTheme.typography.headlineSmall)

                    Spacer(modifier = Modifier.height(8.dp))

                    videojuego.genero?.let {
                        Text(text = "Género: $it", style = MaterialTheme.typography.bodyMedium)
                    }

                    videojuego.plataforma?.let {
                        Text(text = "Plataforma: $it", style = MaterialTheme.typography.bodyMedium)
                    }

                    videojuego.publisher?.let {
                        Text(text = "Publisher: $it", style = MaterialTheme.typography.bodyMedium)
                    }

                    videojuego.releaseDate?.let {
                        Text(text = "Lanzamiento: $it", style = MaterialTheme.typography.bodyMedium)
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = videojuego.descripcion ?: "Sin descripción disponible.",
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 6,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            scope.launch {
                val entity = VideojuegoEntity(
                    id = videojuego.id,
                    nombre = videojuego.nombre,
                    genero = videojuego.genero,
                    descripcion = videojuego.descripcion,
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
