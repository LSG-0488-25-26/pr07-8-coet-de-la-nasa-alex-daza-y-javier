package com.example.api_videojuegos.network

import com.example.api_videojuegos.model.FreeToGameGame
import retrofit2.http.GET

interface FreeToGameApiService {
    @GET("games")
    suspend fun getGames(): List<FreeToGameGame>
}
