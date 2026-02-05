// Kotlin
package com.example.api_videojuegos.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.background
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.graphics.Color
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.example.api_videojuegos.model.DadesAPIItem
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.font.FontWeight

@Composable
fun ItemGeneroVideojuego(
    item: DadesAPIItem,
    onClick: () -> Unit
) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp, horizontal = 8.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp), verticalAlignment = Alignment.CenterVertically) {

            // Imagen (si hay URL) - tamaño fijo
            val imageUrl = item.imagenCaratula
            if (!imageUrl.isNullOrEmpty()) {
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(imageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = item.nombre ?: "Imagen videojuego",
                    modifier = Modifier
                        .size(80.dp)
                        .padding(end = 8.dp),
                    contentScale = ContentScale.Crop
                )
            } else {
                // Placeholder simple cuando no hay imagen
                Box(modifier = Modifier
                    .size(80.dp)
                    .padding(end = 8.dp)
                    .background(Color.LightGray))
            }

            // Texto: nombre y detalles mínimos
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.nombre ?: "Sin título",
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "ID: ${item.id ?: "-"}",
                    maxLines = 1
                )
            }
        }
    }
}
