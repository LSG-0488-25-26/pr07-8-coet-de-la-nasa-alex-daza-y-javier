package com.example.lazycomponents


import PantallaDetalleGenero
import PantallaListaGeneros
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.example.lazycomponents.model.GeneroVideojuego
import com.example.lazycomponents.ui.theme.LazyComponentsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LazyComponentsTheme {
                Main()
            }
        }
    }
}

@Composable
fun Main() {
    var generoSeleccionado by remember { mutableStateOf<GeneroVideojuego?>(null) }

    if (generoSeleccionado == null) {
        PantallaListaGeneros { genero ->
            generoSeleccionado = genero
        }
    } else {
        PantallaDetalleGenero(
            generoVideojuego = generoSeleccionado!!
        ) {
            generoSeleccionado = null
        }

    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LazyComponentsTheme {
        Main()
    }
}