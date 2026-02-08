package com.example.api_videojuegos.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.api_videojuegos.data.AppDatabase
import com.example.api_videojuegos.data.VideojuegoEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class VideojuegoDetalleViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = AppDatabase.getInstance(application).videojuegoDao()

    suspend fun comprobarGuardado(id: Int): Boolean = withContext(Dispatchers.IO) {
        dao.getById(id) != null
    }

    suspend fun guardar(entity: VideojuegoEntity) = withContext(Dispatchers.IO) {
        dao.insert(entity)
    }
}
