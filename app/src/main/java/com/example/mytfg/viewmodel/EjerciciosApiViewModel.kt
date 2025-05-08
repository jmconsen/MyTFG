package com.example.mytfg.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mytfg.model.EjercicioApi
import com.example.mytfg.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import android.util.Log

class EjerciciosApiViewModel : ViewModel() {
    private val _ejercicios = MutableStateFlow<List<EjercicioApi>>(emptyList())
    val ejercicios: StateFlow<List<EjercicioApi>> = _ejercicios

    fun cargarEjercicios() {
        viewModelScope.launch {
            try {
                val lista = RetrofitClient.apiService.getExercises()
                _ejercicios.value = lista
            } catch (e: Exception) {
                Log.e("EjerciciosApiViewModel", "Error al cargar ejercicios", e)
                _ejercicios.value = emptyList()
            }
        }
    }
}
