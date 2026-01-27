package com.example.api_videojuegos.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.api_videojuegos.model.DadesAPIItem
import com.example.api_videojuegos.model.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VideojuegoViewModel : ViewModel() {

    private val _videojuegos = MutableLiveData<List<DadesAPIItem>>()
    val videojuegos: LiveData<List<DadesAPIItem>> get() = _videojuegos

    // GET
    fun cargarVideojuegos() {
        RetrofitClient.api.getVideojuegos().enqueue(object : Callback<List<DadesAPIItem>> {
            override fun onResponse(call: Call<List<DadesAPIItem>>, response: Response<List<DadesAPIItem>>) {
                if(response.isSuccessful){
                    _videojuegos.postValue(response.body())
                }
            }
            override fun onFailure(call: Call<List<DadesAPIItem>>, t: Throwable) {
                println("Error: ${t.message}")
            }
        })
    }

    // POST
    fun agregarVideojuego(videojuego: DadesAPIItem) {
        RetrofitClient.api.addVideojuego(videojuego).enqueue(object : Callback<DadesAPIItem> {
            override fun onResponse(call: Call<DadesAPIItem>, response: Response<DadesAPIItem>) {
                if(response.isSuccessful){
                    cargarVideojuegos() // actualizar lista
                }
            }
            override fun onFailure(call: Call<DadesAPIItem>, t: Throwable) {
                println("Error: ${t.message}")
            }
        })
    }

    // PUT
    fun actualizarVideojuego(id: Int, videojuego: DadesAPIItem) {
        RetrofitClient.api.updateVideojuego(id, videojuego).enqueue(object : Callback<DadesAPIItem> {
            override fun onResponse(call: Call<DadesAPIItem>, response: Response<DadesAPIItem>) {
                if(response.isSuccessful){
                    cargarVideojuegos()
                }
            }
            override fun onFailure(call: Call<DadesAPIItem>, t: Throwable) {
                println("Error: ${t.message}")
            }
        })
    }

    // DELETE
    fun eliminarVideojuego(id: Int) {
        RetrofitClient.api.deleteVideojuego(id).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if(response.isSuccessful){
                    cargarVideojuegos()
                }
            }
            override fun onFailure(call: Call<Void>, t: Throwable) {
                println("Error: ${t.message}")
            }
        })
    }
}
