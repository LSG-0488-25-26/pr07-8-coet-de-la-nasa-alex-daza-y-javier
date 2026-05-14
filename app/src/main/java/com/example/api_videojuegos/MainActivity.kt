 package com.example.api_videojuegos

 import android.os.Bundle
 import androidx.activity.ComponentActivity
 import androidx.activity.compose.setContent
 import androidx.activity.viewModels
 import androidx.activity.enableEdgeToEdge
 import androidx.compose.runtime.*
 import androidx.compose.ui.tooling.preview.Preview
 import com.example.api_videojuegos.model.DadesAPIItem
 import com.example.api_videojuegos.view.PantallaDetalleGenero
 import com.example.api_videojuegos.view.PantallaListaGeneros
 import com.example.api_videojuegos.ui.theme.LazyComponentsTheme
 import com.example.api_videojuegos.viewmodel.VideojuegoViewModel


 class MainActivity : ComponentActivity() {
     override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)
         enableEdgeToEdge()
         val vm: VideojuegoViewModel by viewModels()
         setContent {
             LazyComponentsTheme {
                 Main(vm)
             }
         }
     }
 }

 @Composable
 fun Main(vm: VideojuegoViewModel) {
     var generoSeleccionado by remember { mutableStateOf<DadesAPIItem?>(null) }

     if (generoSeleccionado == null) {
         PantallaListaGeneros(viewModel = vm) { genero ->
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
                PantallaListaGeneros(onGeneroClick = {})
            }
        }