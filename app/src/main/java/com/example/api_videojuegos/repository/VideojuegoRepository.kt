package com.example.api_videojuegos.repository

import com.example.api_videojuegos.data.VideojuegoDao
import com.example.api_videojuegos.data.VideojuegoEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.flow.Flow

class VideojuegoRepository(private val dao: VideojuegoDao) {
    // Guardamos en IO para garantizar no bloquear el hilo UI
    suspend fun save(videojuego: VideojuegoEntity): Long = withContext(Dispatchers.IO) {
        dao.insert(videojuego)
    }

    // Inserción en lote
    suspend fun saveAll(videojuegos: List<VideojuegoEntity>): List<Long> = withContext(Dispatchers.IO) {
        dao.insertAll(videojuegos)
    }

    fun getAll(): Flow<List<VideojuegoEntity>> = dao.getAll()

    suspend fun getById(id: Int): VideojuegoEntity? = withContext(Dispatchers.IO) {
        dao.getById(id)
    }
}