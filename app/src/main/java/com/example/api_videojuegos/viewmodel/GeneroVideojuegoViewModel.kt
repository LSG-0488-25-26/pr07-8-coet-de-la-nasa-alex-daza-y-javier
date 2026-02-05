package com.example.api_videojuegos.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.api_videojuegos.model.DadesAPIItem
import com.example.api_videojuegos.model.RawgGame
import com.example.api_videojuegos.model.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VideojuegoViewModel : ViewModel() {

    private val _videojuegos = MutableLiveData<List<DadesAPIItem>>()
    val videojuegos: LiveData<List<DadesAPIItem>> get() = _videojuegos

    // estado de carga y error
    private val _loading = MutableLiveData<Boolean>(false)
    val loading: LiveData<Boolean> get() = _loading

    private val _error = MutableLiveData<String?>(null)
    val error: LiveData<String?> get() = _error

    // GET (RAWG)
    fun cargarVideojuegos() {
        _loading.postValue(true)
        _error.postValue(null)
        RetrofitClient.api.getVideojuegos(RetrofitClient.API_KEY).enqueue(object : Callback<com.example.api_videojuegos.model.RawgResponse> {
            override fun onResponse(call: Call<com.example.api_videojuegos.model.RawgResponse>, response: Response<com.example.api_videojuegos.model.RawgResponse>) {
                _loading.postValue(false)
                if(response.isSuccessful){
                    val rawg = response.body()
                    val mapped: List<DadesAPIItem> = rawg?.results?.map { rg ->
                        DadesAPIItem(
                            id = rg.id,
                            nombre = rg.name,
                            imagenCaratula = rg.background_image
                        )
                    } ?: emptyList()
                    _videojuegos.postValue(mapped)
                } else {
                    _error.postValue("Error en la respuesta: ${response.code()} ${response.message()}")
                }
            }
            override fun onFailure(call: Call<com.example.api_videojuegos.model.RawgResponse>, t: Throwable) {
                _loading.postValue(false)
                _error.postValue(t.message ?: "Error de red")
            }
        })
    }

    // Mantengo los otros métodos locales si se usan
    fun cargarVideojuegosLocal() {
        RetrofitClient.api.getVideojuegosLocal().enqueue(object : Callback<List<DadesAPIItem>> {
            override fun onResponse(call: Call<List<DadesAPIItem>>, response: Response<List<DadesAPIItem>>) {
                if(response.isSuccessful){
                    _videojuegos.postValue(response.body() ?: emptyList())
                }
            }
            override fun onFailure(call: Call<List<DadesAPIItem>>, t: Throwable) {
                println("Error: ${t.message}")
            }
        })
    }

    // Los métodos POST/PUT/DELETE los mantengo sin cambios
    fun agregarVideojuego(videojuego: DadesAPIItem) {
        RetrofitClient.api.addVideojuego(videojuego).enqueue(object : Callback<DadesAPIItem> {
            override fun onResponse(call: Call<DadesAPIItem>, response: Response<DadesAPIItem>) {
                if(response.isSuccessful){
                    cargarVideojuegosLocal() // actualizar lista local
                }
            }
            override fun onFailure(call: Call<DadesAPIItem>, t: Throwable) {
                println("Error: ${t.message}")
            }
        })
    }

    fun actualizarVideojuego(id: Int, videojuego: DadesAPIItem) {
        RetrofitClient.api.updateVideojuego(id, videojuego).enqueue(object : Callback<DadesAPIItem> {
            override fun onResponse(call: Call<DadesAPIItem>, response: Response<DadesAPIItem>) {
                if(response.isSuccessful){
                    cargarVideojuegosLocal()
                }
            }
            override fun onFailure(call: Call<DadesAPIItem>, t: Throwable) {
                println("Error: ${t.message}")
            }
        })
    }

    fun eliminarVideojuego(id: Int) {
        RetrofitClient.api.deleteVideojuego(id).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if(response.isSuccessful){
                    cargarVideojuegosLocal()
                }
            }
            override fun onFailure(call: Call<Void>, t: Throwable) {
                println("Error: ${t.message}")
            }
        })
    }
}
