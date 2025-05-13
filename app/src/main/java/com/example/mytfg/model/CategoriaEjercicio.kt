package com.example.mytfg.model

data class CategoriaEjercicio(
    val bodyPart: String,
    val nivel: String,
    val imagenUrl: String
)

data class NivelEjercicio(
    val nivel: String,
    val imagenResId: Int
)

data class CategoriaGrupo(
    val nombre: String,
    val niveles: List<NivelEjercicio>
)
