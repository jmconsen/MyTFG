package com.example.mytfg.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DietaViewModel : ViewModel() {

    private val _objetivoSeleccionado = MutableStateFlow<String?>(null)
    val objetivoSeleccionado: StateFlow<String?> = _objetivoSeleccionado.asStateFlow()

    private val _contenidoDieta = MutableStateFlow<String?>(null)
    val contenidoDieta: StateFlow<String?> = _contenidoDieta.asStateFlow()

    fun seleccionarObjetivo(objetivo: String) {
        _objetivoSeleccionado.value = objetivo
    }

    fun cargarDieta(claveDocumento: String) {
        viewModelScope.launch {
            Firebase.firestore.collection("dietas").document(claveDocumento).get()
                .addOnSuccessListener { documento ->
                    if (documento != null && documento.exists()) {
                        val contenido = documento.getString("contenido")
                        _contenidoDieta.value = contenido ?: "No se encontró contenido."
                    } else {
                        _contenidoDieta.value = "Dieta no encontrada en la base de datos."
                    }
                }
                .addOnFailureListener { e ->
                    _contenidoDieta.value = "Error al cargar la dieta: ${e.message}"
                }
        }
    }
}
