
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
import coil.compose.AsyncImage
import com.example.api_videojuegos.model.DadesAPIItem
import java.lang.reflect.Field
import java.util.*

@Composable
fun PantallaDetalleGenero(
    generoVideojuego: DadesAPIItem,
    onVolver: () -> Unit
) {
    // Helpers de reflexión para cubrir nombres distintos en el modelo
    fun findInt(obj: Any, candidates: List<String>): Int? {
        for (name in candidates) {
            val getterName = "get" + name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
            try {
                val m = obj.javaClass.getMethod(getterName)
                val value = m.invoke(obj)
                if (value is Number) return value.toInt()
            } catch (_: Exception) { }
            try {
                val f: Field = obj.javaClass.getDeclaredField(name)
                f.isAccessible = true
                val value = f.get(obj)
                if (value is Number) return value.toInt()
            } catch (_: Exception) { }
        }
        return null
    }

    fun findString(obj: Any, candidates: List<String>): String? {
        for (name in candidates) {
            val getterName = "get" + name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
            try {
                val m = obj.javaClass.getMethod(getterName)
                val value = m.invoke(obj)
                if (value != null) return value.toString()
            } catch (_: Exception) { }
            try {
                val f: Field = obj.javaClass.getDeclaredField(name)
                f.isAccessible = true
                val value = f.get(obj)
                if (value != null) return value.toString()
            } catch (_: Exception) { }
        }
        return null
    }

    // Nombres candidatos (ajusta si conoces los reales)
    val imageCandidates = listOf("imagenRes", "imagen", "imageRes", "image", "imagenResId", "imagen_id", "image_id", "resId")
    val titleCandidates = listOf("nombreJuego", "nombre", "title", "name")
    val genreCandidates = listOf("genero", "genre", "tipo")
    val descCandidates = listOf("descripcion", "description", "desc", "detalle", "summary")

    val imageRes = findInt(generoVideojuego, imageCandidates) ?: android.R.drawable.ic_menu_report_image
    val nombreJuego = findString(generoVideojuego, titleCandidates) ?: generoVideojuego.toString()
    val generoText = findString(generoVideojuego, genreCandidates) ?: ""
    val descripcion = findString(generoVideojuego, descCandidates) ?: ""

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = generoVideojuego.imagenCaratula,
            contentDescription = nombreJuego,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = nombreJuego,
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center
        )

        if (generoText.isNotEmpty()) {
            Text(
                text = "Género: $generoText",
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        if (descripcion.isNotEmpty()) {
            Text(
                text = descripcion,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onVolver,
            modifier = Modifier.fillMaxWidth(0.5f)
        ) {
            Text("Volver")
        }
    }
}
