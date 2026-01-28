 package com.example.api_videojuegos

 import android.os.Bundle
 import androidx.activity.ComponentActivity
 import androidx.activity.compose.setContent
 import androidx.activity.enableEdgeToEdge
 import androidx.compose.runtime.*
 import androidx.compose.ui.tooling.preview.Preview
 import com.example.api_videojuegos.model.DadesAPIItem
 import com.example.api_videojuegos.view.PantallaDetalleGenero
 import com.example.api_videojuegos.view.PantallaListaGeneros
 import com.example.api_videojuegos.ui.theme.LazyComponentsTheme


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
     var generoSeleccionado by remember { mutableStateOf<DadesAPIItem?>(null) }

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