// kotlin
package com.example.api_videojuegos.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.api_videojuegos.data.VideojuegoEntity
import com.example.api_videojuegos.repository.VideojuegoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class VideojuegoDetalleViewModel(application: Application) : AndroidViewModel(application) {
    private val repo: VideojuegoRepository

    init {
        val db = AppDatabase.getInstance(application)
        repo = VideojuegoRepository(db.videojuegoDao())
    }

    private val _guardado = MutableStateFlow(false)
    val guardado: StateFlow<Boolean> = _guardado

    fun guardar(videojuego: VideojuegoEntity) {
        viewModelScope.launch {
            repo.save(videojuego)
            _guardado.value = true
        }
    }

    suspend fun comprobarGuardado(id: Int): Boolean {
        return repo.getById(id) != null
    }
}