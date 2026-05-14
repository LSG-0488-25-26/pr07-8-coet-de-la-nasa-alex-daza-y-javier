package com.example.api_videojuegos.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.api_videojuegos.model.DadesAPIItem
import com.example.api_videojuegos.network.RetrofitInstance
import com.example.api_videojuegos.data.AppDatabase
import com.example.api_videojuegos.data.VideojuegoEntity
import com.example.api_videojuegos.repository.VideojuegoRepository
import android.util.Log
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull
import kotlinx.coroutines.Dispatchers

class VideojuegoViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = AppDatabase.getInstance(application).videojuegoDao()
    private val repository = VideojuegoRepository(dao)
    private val _videojuegos = MutableLiveData<List<DadesAPIItem>>()
    val videojuegos: LiveData<List<DadesAPIItem>> get() = _videojuegos
    private val _loading = MutableLiveData<Boolean>(false)
    val loading: LiveData<Boolean> get() = _loading
    private val _error = MutableLiveData<String?>(null)
    val error: LiveData<String?> get() = _error

    init {
        viewModelScope.launch {
            repository.getAll().collect { list ->
                val mapped = list.map { e ->
                    DadesAPIItem(
                        id = e.id,
                        nombre = e.nombre,
                        imagenCaratula = e.thumbnail,
                        descripcion = e.descripcion,
                        genero = e.genero
                    )
                }
                _videojuegos.postValue(mapped)
            }
        }
    }
    fun cargarVideojuegos() {
        Log.d("VideojuegoVM", "cargarVideojuegos: inicio")
        _loading.postValue(true)
        _error.postValue(null)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val games = withTimeoutOrNull(10_000) {
                    RetrofitInstance.api.getGames()
                }
                if (games == null) {
                    Log.d("VideojuegoVM", "cargarVideojuegos: timeout o nulo")
                    _error.postValue("La petición tardó demasiado")
                    _loading.postValue(false)
                    return@launch
                }
                Log.d("VideojuegoVM", "cargarVideojuegos: respuesta recibida, tamaño=${games.size}")
                val mapped = games.map { g ->
                    DadesAPIItem(
                        id = g.id,
                        nombre = g.title,
                        imagenCaratula = g.thumbnail
                    )
                }
                val entities = mapped.map { item ->
                    VideojuegoEntity(
                        id = item.id,
                        nombre = item.nombre,
                        genero = item.genero,
                        descripcion = item.descripcion,
                        thumbnail = item.imagenCaratula
                    )
                }
                try {
                    val res = repository.saveAll(entities)
                    Log.d("VideojuegoVM", "cargarVideojuegos: guardados en BD, ids=${res}")
                } catch (dbEx: Exception) {
                    Log.e("VideojuegoVM", "Error guardando en BD", dbEx)
                    _error.postValue(dbEx.message ?: "Error al guardar en BD")
                }
                _videojuegos.postValue(mapped)
            } catch (e: Exception) {
                Log.e("VideojuegoVM", "Error al cargar juegos", e)
                _error.postValue(e.message ?: "Error de red")
            } finally {
                Log.d("VideojuegoVM", "cargarVideojuegos: finalizando, setting loading=false")
                _loading.postValue(false)
            }
        }
    }
}