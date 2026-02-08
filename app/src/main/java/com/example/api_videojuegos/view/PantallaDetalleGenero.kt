
        package com.example.api_videojuegos.view

        import androidx.compose.foundation.layout.*
        import androidx.compose.material3.*
        import androidx.compose.runtime.Composable
        import androidx.compose.ui.Alignment
        import androidx.compose.ui.Modifier
        import androidx.compose.ui.layout.ContentScale
        import androidx.compose.ui.text.style.TextAlign
        import androidx.compose.ui.unit.dp
        import coil.compose.AsyncImage
        import com.example.api_videojuegos.model.DadesAPIItem
        import androidx.compose.foundation.background
        import androidx.compose.ui.graphics.Color

        @Composable
        fun PantallaDetalleGenero(
            generoVideojuego: DadesAPIItem?,
            onVolver: () -> Unit
        ) {
            if (generoVideojuego == null) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No hay datos", textAlign = TextAlign.Center)
                }
                return
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 24.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                val imageUrl = generoVideojuego.imagenCaratula
                if (!imageUrl.isNullOrEmpty()) {
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = generoVideojuego.nombre ?: "Imagen videojuego",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                            .background(Color.LightGray),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "Sin imagen")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = generoVideojuego.nombre ?: "Sin título",
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "ID: ${generoVideojuego.id}",
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = onVolver,
                    modifier = Modifier.fillMaxWidth(0.5f)
                ) {
                    Text("Volver")
                }
            }
        }