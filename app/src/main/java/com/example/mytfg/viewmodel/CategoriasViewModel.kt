package com.example.mytfg.viewmodel

import androidx.lifecycle.ViewModel
import com.example.mytfg.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class CategoriaGrupo(
    val nombre: String,
    val imagenResId: Int,
    val equipos: List<String> = emptyList() // Lista de equipos por defecto vacía
)

class CategoriasViewModel : ViewModel() {
    private val _categorias = MutableStateFlow<List<CategoriaGrupo>>(emptyList())
    val categorias: StateFlow<List<CategoriaGrupo>> = _categorias

    init {
        _categorias.value = listOf(
            CategoriaGrupo(
                nombre = "Piernas superiores",
                imagenResId = R.drawable.piernasfacil,
                equipos = listOf("body weight", "barbell")
            ),
            CategoriaGrupo(
                nombre = "Piernas inferiores",
                imagenResId = R.drawable.piernasinferior1,
                equipos = listOf("body weight", "dumbbell", "barbell")
            ),
            CategoriaGrupo(
                nombre = "Cardio",
                imagenResId = R.drawable.cardiofacil,
                equipos = listOf("body weight", "dumbbell")
            ),
            CategoriaGrupo(
                nombre = "Torso",
                imagenResId = R.drawable.torsofacil,
                equipos = listOf("body weight", "barbell", "medicine ball")
            ),
            CategoriaGrupo(
                nombre = "Brazos",
                imagenResId = R.drawable.brazosmedio,
                equipos = listOf("barbell")
            ),
            CategoriaGrupo(
                nombre = "Antebrazo",
                imagenResId = R.drawable.antebrazofacil,
                equipos = listOf("dumbbell", "barbell", "cable")
            ),
            CategoriaGrupo(
                nombre = "Espalda",
                imagenResId = R.drawable.espaldafacil,
                equipos = listOf("barbell", "cable")
            )
        )
    }
}

/*
class CategoriasViewModel : ViewModel() {
    private val _categorias = MutableStateFlow<List<CategoriaGrupo>>(emptyList())
    val categorias: StateFlow<List<CategoriaGrupo>> = _categorias

    init {
        _categorias.value = listOf(
            CategoriaGrupo("Piernas superiores", R.drawable.piernasfacil),
            CategoriaGrupo("Piernas inferiores", R.drawable.piernasinferior1),
            CategoriaGrupo("Cardio", R.drawable.cardiofacil),
            CategoriaGrupo("Torso", R.drawable.torsofacil),
            CategoriaGrupo("Brazos", R.drawable.brazosfacil),
            CategoriaGrupo("Antebrazo", R.drawable.antebrazofacil),
            CategoriaGrupo("Espalda", R.drawable.espaldafacil)
        )
    }
}

 */
