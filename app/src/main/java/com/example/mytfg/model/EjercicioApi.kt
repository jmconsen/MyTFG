package com.example.mytfg.model

data class EjercicioApi(
    val id: String,
    val name: String,
    val bodyPart: String,
    val equipment: String,
    val gifUrl: String,
    val target: String,
    val instructions: List<String>? = null // <--- Añade esto si no está
)


