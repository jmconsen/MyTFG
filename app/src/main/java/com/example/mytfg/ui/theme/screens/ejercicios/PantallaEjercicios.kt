package com.example.mytfg.ui.theme.screens.ejercicios

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.mytfg.viewmodel.EjerciciosApiViewModel
import com.example.mytfg.viewmodel.EjerciciosApiViewModelFactory
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

// Si tienes la función getApiBodyPart en un util, impórtala.
// Si no, puedes dejarla aquí.
fun getApiBodyPart(bodyPart: String): String = when (bodyPart.lowercase()) {
    "piernas" -> "legs"
    "torso" -> "waist" // O "back", "chest", según tu lógica
    "brazos" -> "upper arms"
    "antebrazo" -> "forearms"
    "espalda" -> "back"
    else -> bodyPart.lowercase()
}

@Composable
fun PantallaEjercicios(
    navHostController: NavHostController,
    bodyPart: String,
    nivel: String
) {
    // SOLO ESTA LÍNEA para crear el ViewModel con factory:
    val viewModel: EjerciciosApiViewModel = viewModel(factory = EjerciciosApiViewModelFactory())

    val bodyPartApi = getApiBodyPart(bodyPart)

    // Llama a la API al entrar en la pantalla
    LaunchedEffect(bodyPartApi) {
        viewModel.cargarEjerciciosPorBodyPart(bodyPartApi)
    }

    val ejercicios by viewModel.ejercicios.collectAsState()

    if (ejercicios.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "No hay ejercicios para ${bodyPart.replaceFirstChar { it.uppercase() }} - $nivel",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(ejercicios) { ejercicio ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .clickable {
                            navHostController.navigate("PantallaDetalleEjercicio/${ejercicio.id}")
                        }
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsyncImage(
                            model = ejercicio.gifUrl,
                            contentDescription = ejercicio.name,
                            modifier = Modifier.size(80.dp)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(
                                text = ejercicio.name.replaceFirstChar { it.uppercase() },
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                text = ejercicio.target.replaceFirstChar { it.uppercase() },
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
        }
    }
}
