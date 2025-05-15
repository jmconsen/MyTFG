package com.example.mytfg.util

fun getApiBodyPart(nombre: String): String = when (nombre.lowercase()) {
    "piernas superiores" -> "upper legs"
    "piernas inferiores" -> "lower legs"
    "torso" -> "waist"
    "brazos" -> "upper arms"
    "espalda" -> "back"
    "antebrazo" -> "lower arms"
    "cardio" -> "cardio"
    else -> nombre.lowercase()
}
