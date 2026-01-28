
package com.example.api_videojuegos.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.api_videojuegos.model.DadesAPIItem
import com.example.lazycomponents.model.GeneroVideojuego


//Muestra la información completa de un videojuego seleccionado
@Composable
fun PantallaDetalleGenero(
    generoVideojuego: DadesAPIItem,
    onVolver: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(GeneroVideojuego.imagenRes),
            contentDescription = GeneroVideojuego.nombreJuego,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = generoVideojuego.nombreJuego,
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center
        )

        Text(
            text = "Género: ${generoVideojuego.genero}",
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = generoVideojuego.descripcion,
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
