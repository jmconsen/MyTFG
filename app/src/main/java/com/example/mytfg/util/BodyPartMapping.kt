package com.example.mytfg.util

fun getApiBodyPart(nombre: String): String = when (nombre.lowercase()) {
    "piernas" -> "legs"
    "torso" -> "waist" // o "back", "chest", según tu lógica
    "brazos" -> "upper arms"
    "espalda" -> "back"
    "antebrazo" -> "forearms"
    else -> nombre.lowercase()
}
