package com.example.mytfg.viewmodel

import androidx.lifecycle.ViewModel
import com.example.mytfg.R
import com.example.mytfg.model.CategoriaGrupo
import com.example.mytfg.model.NivelEjercicio
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CategoriasViewModel : ViewModel() {
    private val _categorias = MutableStateFlow<List<CategoriaGrupo>>(emptyList())
    val categorias: StateFlow<List<CategoriaGrupo>> = _categorias

    init {
        _categorias.value = listOf(
            CategoriaGrupo(
                nombre = "Piernas superiores",
                niveles = listOf(
                    NivelEjercicio("Principiante", R.drawable.piernasfacil),
                    NivelEjercicio("Medio", R.drawable.piernasmedio),
                    NivelEjercicio("Avanzado", R.drawable.piernasavanzado)
                )
            ),
            CategoriaGrupo(
                nombre = "Piernas inferiores",
                niveles = listOf(
                    NivelEjercicio("Principiante", R.drawable.piernasinferior1),
                    NivelEjercicio("Medio", R.drawable.piernasinferior3),
                    NivelEjercicio("Avanzado", R.drawable.piernasinferior2)
                )
            ),
            CategoriaGrupo(
                nombre = "Cardio",
                niveles = listOf(
                    NivelEjercicio("Principiante", R.drawable.cardiofacil),
                    NivelEjercicio("Medio", R.drawable.cardiomedio),
                    NivelEjercicio("Avanzado", R.drawable.cardio3)
                )
            ),
            CategoriaGrupo(
                nombre = "Torso",
                niveles = listOf(
                    NivelEjercicio("Principiante", R.drawable.torsofacil),
                    NivelEjercicio("Medio", R.drawable.torsomedio),
                    NivelEjercicio("Avanzado", R.drawable.torsoavanzado)
                )
            ),
            CategoriaGrupo(
                nombre = "Brazos",
                niveles = listOf(
                    NivelEjercicio("Principiante", R.drawable.brazosfacil),
                    NivelEjercicio("Medio", R.drawable.brazosmedio),
                    NivelEjercicio("Avanzado", R.drawable.brazosavanzado)
                )
            ),
            CategoriaGrupo(
                nombre = "Antebrazo",
                niveles = listOf(
                    NivelEjercicio("Principiante", R.drawable.antebrazofacil),
                    NivelEjercicio("Medio", R.drawable.antebrazomedio),
                    NivelEjercicio("Avanzado", R.drawable.antebrazosavanzado)
                )
            ),
            CategoriaGrupo(
                nombre = "Espalda",
                niveles = listOf(
                    NivelEjercicio("Principiante", R.drawable.espaldafacil),
                    NivelEjercicio("Medio", R.drawable.espaldamedio),
                    NivelEjercicio("Avanzado", R.drawable.espaldavanzado)
                )
            )
        )
    }
}
