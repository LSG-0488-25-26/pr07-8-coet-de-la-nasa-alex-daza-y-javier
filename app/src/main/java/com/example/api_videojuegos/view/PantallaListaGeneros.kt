import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lazycomponents.model.GeneroVideojuego
import com.example.lazycomponents.viewmodel.GeneroVideojuegoViewModel

@Composable
fun PantallaListaGeneros(
    onGeneroClick: (GeneroVideojuego) -> Unit
) {
    val viewModel: GeneroVideojuegoViewModel = viewModel()
    val listaGeneros by viewModel.listaGeneros.observeAsState(initial = emptyList())

    // Lista de géneros de videojuegos
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(listaGeneros) { genero ->
            ItemGeneroVideojuego(
                generoVideojuego = genero,
                onClick = { onGeneroClick(genero) }
            )
        }
    }
}
