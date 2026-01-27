package com.example.api_videojuegos.model


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.api_videojuegos.model.DadesAPI
import retrofit2.Call
import retrofit2.http.*

interface VideojuegoApi {

    @GET("videojuegos")
    fun getVideojuegos(): Call<List<DadesAPIItem>>

    @POST("videojuegos")
    fun addVideojuego(@Body videojuego: DadesAPIItem): Call<DadesAPIItem>

    @PUT("videojuegos/{id}")
    fun updateVideojuego(@Path("id") id: Int, @Body videojuego: DadesAPIItem): Call<DadesAPIItem>

    @DELETE("videojuegos/{id}")
    fun deleteVideojuego(@Path("id") id: Int): Call<Void>
}

object RetrofitClient {
    private const val BASE_URL = "https://tuapi.com/"

    val api: VideojuegoApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(VideojuegoApi::class.java)
    }
}


