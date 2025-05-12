package com.example.mytfg.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mytfg.model.EjercicioApi
import com.example.mytfg.network.ExerciseApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EjerciciosApiViewModel(
    private val apiService: ExerciseApiService
) : ViewModel() {

    private val _ejercicios = MutableStateFlow<List<EjercicioApi>>(emptyList())
    val ejercicios: StateFlow<List<EjercicioApi>> = _ejercicios

    fun cargarEjerciciosPorBodyPart(bodyPart: String) {
        viewModelScope.launch {
            try {
                val resultado = apiService.getExercisesByBodyPart(bodyPart)
                _ejercicios.value = resultado
            } catch (e: Exception) {
                _ejercicios.value = emptyList()
            }
        }
    }
}
