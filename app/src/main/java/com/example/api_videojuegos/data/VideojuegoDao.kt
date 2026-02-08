package com.example.api_videojuegos.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface VideojuegoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(videojuego: VideojuegoEntity)

    @Query("SELECT * FROM videojuegos ORDER BY nombre")
    fun getAll(): Flow<List<VideojuegoEntity>>

    @Query("SELECT * FROM videojuegos WHERE id = :id LIMIT 1")
    suspend fun getById(id: Int): VideojuegoEntity?
}