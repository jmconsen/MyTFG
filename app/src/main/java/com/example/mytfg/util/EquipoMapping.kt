package com.example.mytfg.util

val equipoTraduccion = mapOf(
    "body weight" to "Peso corporal",
    "dumbbell" to "Mancuerna",
    "barbell" to "Barra",
    "kettlebell" to "Kettlebell",
    "cable" to "Polea",
    "machine" to "Máquina",
    "band" to "Banda elástica",
    "medicine ball" to "Balón medicinal",
    "assisted" to "Asistido"
)

fun traducirEquipo(equipo: String): String {
    return equipoTraduccion[equipo.lowercase()] ?: equipo.replaceFirstChar { it.uppercase() }
}
