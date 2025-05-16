package com.example.mytfg.viewmodel

import androidx.lifecycle.ViewModel
import com.example.mytfg.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class CategoriaGrupo(
    val nombre: String,
    val imagenResId: Int
)

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
