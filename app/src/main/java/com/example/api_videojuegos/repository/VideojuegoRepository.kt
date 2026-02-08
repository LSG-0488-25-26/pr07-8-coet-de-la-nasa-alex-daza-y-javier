package com.example.api_videojuegos.repository

import com.example.api_videojuegos.data.VideojuegoDao
import com.example.api_videojuegos.data.VideojuegoEntity
import kotlinx.coroutines.flow.Flow

class VideojuegoRepository(private val dao: VideojuegoDao) {
    suspend fun save(videojuego: VideojuegoEntity) = dao.insert(videojuego)
    fun getAll(): Flow<List<VideojuegoEntity>> = dao.getAll()
    suspend fun getById(id: Int): VideojuegoEntity? = dao.getById(id)
}