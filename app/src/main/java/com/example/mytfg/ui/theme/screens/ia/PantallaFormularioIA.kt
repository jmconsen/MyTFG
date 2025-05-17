package com.example.mytfg.ui.theme.screens.ia

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mytfg.util.generarPlanEntrenamientoIA
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.graphics.Color
import android.util.Log

private const val TAG = "PantallaFormularioIA"

@Composable
fun PantallaFormularioIA(
    navHostController: NavHostController,
    paddingValues: PaddingValues = PaddingValues()
) {
    var edad by remember { mutableStateOf("") }
    var peso by remember { mutableStateOf("") }
    var altura by remember { mutableStateOf("") }
    var frecuencia by remember { mutableStateOf("") }
    var objetivo by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }
    var resultado by remember { mutableStateOf<String?>(null) }
    var error by remember { mutableStateOf<String?>(null) }

    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp)
    ) {
        Text("Completa tus datos", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(16.dp))
        OutlinedTextField(value = edad, onValueChange = { edad = it }, label = { Text("Edad") })
        OutlinedTextField(value = peso, onValueChange = { peso = it }, label = { Text("Peso (kg)") })
        OutlinedTextField(value = altura, onValueChange = { altura = it }, label = { Text("Altura (cm)") })
        OutlinedTextField(value = frecuencia, onValueChange = { frecuencia = it }, label = { Text("Frecuencia de ejercicio (días/semana)") })
        OutlinedTextField(value = objetivo, onValueChange = { objetivo = it }, label = { Text("Objetivo (ej: ganar músculo)") })
        Spacer(Modifier.height(24.dp))
        Button(
            onClick = {
                loading = true
                resultado = null
                error = null
                Log.d(TAG, "Llamando a generarPlanEntrenamientoIA (HuggingFace)")
                coroutineScope.launch {
                    try {
                        val plan = generarPlanEntrenamientoIA(edad, peso, altura, frecuencia, objetivo)
                        Log.d(TAG, "Respuesta de la IA: $plan")
                        resultado = plan
                        if (plan == null) {
                            error = "No se pudo generar el plan. Revisa tu conexión o los límites de HuggingFace."
                        }
                    } catch (e: Exception) {
                        Log.e(TAG, "Error al generar el plan: ${e.message}", e)
                        error = "Error inesperado: ${e.message}"
                    }
                    loading = false
                }
            },
            enabled = !loading
        ) {
            Text("Generar plan con IA")
        }
        if (loading) {
            CircularProgressIndicator(Modifier.padding(16.dp))
        }
        error?.let {
            Spacer(Modifier.height(16.dp))
            Text(it, color = Color.Red)
        }
        resultado?.let {
            Spacer(Modifier.height(16.dp))
            Text("Plan generado:", style = MaterialTheme.typography.titleMedium)
            Text(it)
        }
    }
}
