package com.example.mytfg.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mytfg.model.Ejercicio
import com.example.mytfg.repository.EjerciciosRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EjerciciosViewModel : ViewModel() {
    private val repository = EjerciciosRepository()
    private val _ejercicios = MutableStateFlow<List<Ejercicio>>(emptyList())
    val ejercicios: StateFlow<List<Ejercicio>> = _ejercicios

    fun cargarEjercicios() {
        repository.getEjercicios { lista ->
            _ejercicios.value = lista
        }
    }
}
