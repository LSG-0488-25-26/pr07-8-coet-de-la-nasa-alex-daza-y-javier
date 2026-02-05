package com.example.api_videojuegos.model

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call
import retrofit2.http.*

interface VideojuegoApi {

    // RAWG: pedimos la lista de juegos en el endpoint /games con la API key como query param
    @GET("games")
    fun getVideojuegos(@Query("key") apiKey: String): Call<RawgResponse>

    // Métodos CRUD locales (mantener para compatibilidad)
    @GET("videojuegos")
    fun getVideojuegosLocal(): Call<List<DadesAPIItem>>

    @POST("videojuegos")
    fun addVideojuego(@Body videojuego: DadesAPIItem): Call<DadesAPIItem>

    @PUT("videojuegos/{id}")
    fun updateVideojuego(@Path("id") id: Int, @Body videojuego: DadesAPIItem): Call<DadesAPIItem>

    @DELETE("videojuegos/{id}")
    fun deleteVideojuego(@Path("id") id: Int): Call<Void>
}

object RetrofitClient {
    private const val BASE_URL = "https://api.rawg.io/api/"
    const val API_KEY = "8feeec42e5af452a8ab057a2dbf2689a"

    val api: VideojuegoApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(VideojuegoApi::class.java)
    }
}
