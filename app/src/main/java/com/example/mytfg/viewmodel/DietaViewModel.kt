package com.example.mytfg.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DietaViewModel : ViewModel() {
    // Recomendaciones de dieta
    private val dietas = mapOf(
        "aumentar_peso" to """
            🔋 Dieta hipercalórica:
            - 3000-3500 kcal/día
            - Alto en carbohidratos complejos
            - 2g proteína/kg peso
            - Ejemplo desayuno: Avena, plátano, frutos secos, leche entera
            - Ejemplo comida: Pasta integral, pollo, verduras, aceite de oliva
        """.trimIndent(),
        "reducir_peso" to """
            ⚖️ Dieta hipocalórica:
            - 1800-2200 kcal/día
            - Enfocada en proteínas magras
            - Reducción de carbohidratos simples
            - Ejemplo desayuno: Yogur desnatado, frutos rojos, semillas de chía
            - Ejemplo comida: Pescado blanco, ensalada variada, quinoa
        """.trimIndent(),
        "tonificar" to """
            💪 Dieta de tonificación:
            - 2500-2800 kcal/día
            - 40% proteínas, 40% carbos, 20% grasas
            - Ejemplo desayuno: Tortilla de claras, pan integral, tomate
            - Ejemplo comida: Ternera magra, arroz integral, brócoli
        """.trimIndent(),
        "ganar_masa_muscular" to """
            🏋️ Dieta para ganar masa muscular:
        - 3200-3700 kcal/día
        - Alto en proteínas (2-2.5g/kg peso)
        - Carbohidratos complejos en cada comida
        - Grasas saludables: aguacate, frutos secos, aceite de oliva
        - Ejemplo desayuno: Tortilla de 4 claras y 2 huevos, avena, plátano
        - Ejemplo comida: Arroz integral, salmón, brócoli, aceite de oliva
    """.trimIndent(),
        "mantener_peso" to """
            🔄 Dieta equilibrada:
            - 2500 kcal/día
            - 5 comidas al día
            - 30 minutos de ejercicio diario
            - Ejemplo desayuno: Pan integral, aguacate, huevo cocido
            - Ejemplo comida: Lentejas, arroz, verduras asadas
        """.trimIndent()
    )

    // Estado para la selección de objetivo (para PantallaSeleccionDieta)
    private val _objetivoSeleccionado = MutableStateFlow<String?>(null)
    val objetivoSeleccionado: StateFlow<String?> = _objetivoSeleccionado.asStateFlow()

    // Estado para el contenido de la dieta (para PantallaDieta)
    private val _contenidoDieta = MutableStateFlow<String?>(null)
    val contenidoDieta: StateFlow<String?> = _contenidoDieta.asStateFlow()

    // Función para seleccionar objetivo (PantallaSeleccionDieta)
    fun seleccionarObjetivo(objetivo: String) {
        _objetivoSeleccionado.value = objetivo
    }

    // Función para cargar la dieta según el objetivo (PantallaDieta)
    fun cargarDieta(objetivo: String) {
        _contenidoDieta.value = dietas[objetivo]
    }
}
