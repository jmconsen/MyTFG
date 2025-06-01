package com.example.mytfg.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EntrenamientoViewModel : ViewModel() {

    private val _objetivoSeleccionado = MutableStateFlow<String?>(null)
    val objetivoSeleccionado: StateFlow<String?> = _objetivoSeleccionado.asStateFlow()

    private val _contenidoEntrenamiento = MutableStateFlow<String?>(null)
    val contenidoEntrenamiento: StateFlow<String?> = _contenidoEntrenamiento.asStateFlow()

    fun seleccionarObjetivo(objetivo: String) {
        _objetivoSeleccionado.value = objetivo
    }

    fun cargarEntrenamiento(claveDocumento: String) {
        viewModelScope.launch {
            Firebase.firestore.collection("entrenamientos").document(claveDocumento).get()
                .addOnSuccessListener { documento ->
                    if (documento != null && documento.exists()) {
                        val contenido = documento.getString("contenido")
                        _contenidoEntrenamiento.value = contenido ?: "No se encontró contenido."
                    } else {
                        _contenidoEntrenamiento.value = "Entrenamiento no encontrado en la base de datos."
                    }
                }
                .addOnFailureListener { e ->
                    _contenidoEntrenamiento.value = "Error al cargar el entrenamiento: ${e.message}"
                }
        }
    }
}
