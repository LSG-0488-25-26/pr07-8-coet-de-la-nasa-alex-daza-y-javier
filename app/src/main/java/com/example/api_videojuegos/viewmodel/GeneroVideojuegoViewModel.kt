// kotlin
                        package com.example.api_videojuegos.viewmodel

                        import androidx.lifecycle.LiveData
                        import androidx.lifecycle.MutableLiveData
                        import androidx.lifecycle.ViewModel
                        import androidx.lifecycle.viewModelScope
                        import com.example.api_videojuegos.model.DadesAPIItem
                        import com.example.api_videojuegos.network.RetrofitInstance
                        import kotlinx.coroutines.launch

                        class VideojuegoViewModel : ViewModel() {

                            private val _videojuegos = MutableLiveData<List<DadesAPIItem>>()
                            val videojuegos: LiveData<List<DadesAPIItem>> get() = _videojuegos

                            private val _loading = MutableLiveData<Boolean>(false)
                            val loading: LiveData<Boolean> get() = _loading

                            private val _error = MutableLiveData<String?>(null)
                            val error: LiveData<String?> get() = _error

                            fun cargarVideojuegos() {
                                _loading.postValue(true)
                                _error.postValue(null)
                                viewModelScope.launch {
                                    try {
                                        val games = RetrofitInstance.api.getGames()
                                        val mapped = games.map { g ->
                                            DadesAPIItem(
                                                id = g.id,
                                                nombre = g.title,
                                                imagenCaratula = g.thumbnail
                                            )
                                        }
                                        _videojuegos.postValue(mapped)
                                    } catch (e: Exception) {
                                        _error.postValue(e.message ?: "Error de red")
                                    } finally {
                                        _loading.postValue(false)
                                    }
                                }
                            }
                        }