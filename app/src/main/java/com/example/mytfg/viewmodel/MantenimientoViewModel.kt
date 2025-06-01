package com.example.mytfg.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MantenimientoViewModel : ViewModel() {

    private val _objetivoSeleccionado = MutableStateFlow<String?>(null)
    val objetivoSeleccionado: StateFlow<String?> = _objetivoSeleccionado.asStateFlow()

    private val _contenidoMantenimiento = MutableStateFlow<String?>(null)
    val contenidoMantenimiento: StateFlow<String?> = _contenidoMantenimiento.asStateFlow()

    fun seleccionarObjetivo(objetivo: String) {
        _objetivoSeleccionado.value = objetivo
    }

    fun cargarMantenimiento(claveDocumento: String) {
        viewModelScope.launch {
            Firebase.firestore.collection("mantenimientos").document(claveDocumento).get()
                .addOnSuccessListener { documento ->
                    if (documento != null && documento.exists()) {
                        val contenido = documento.getString("contenido")
                        _contenidoMantenimiento.value = contenido ?: "No se encontró contenido."
                    } else {
                        _contenidoMantenimiento.value = "Mantenimiento no encontrado en la base de datos."
                    }
                }
                .addOnFailureListener { e ->
                    _contenidoMantenimiento.value = "Error al cargar el mantenimiento: ${e.message}"
                }
        }
    }
}
