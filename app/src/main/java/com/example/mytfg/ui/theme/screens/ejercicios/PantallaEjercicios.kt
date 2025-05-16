package com.example.mytfg.ui.theme.screens.ejercicios

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.mytfg.viewmodel.EjerciciosApiViewModel
import com.example.mytfg.viewmodel.EjerciciosApiViewModelFactory
import coil.compose.AsyncImage
import coil.request.ImageRequest
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale

@Composable
fun PantallaEjercicios(
    navHostController: NavHostController,
    bodyPart: String,
    equipo: String,
    ejerciciosApiViewModel: EjerciciosApiViewModel = viewModel(factory = EjerciciosApiViewModelFactory()),
    paddingValues: PaddingValues = PaddingValues()
) {
    // Cargar ejercicios solo de la parte seleccionada
    LaunchedEffect(bodyPart) {
        ejerciciosApiViewModel.cargarEjerciciosPorBodyPart(bodyPart)
    }

    // Obtener la lista de ejercicios del ViewModel
    val ejercicios by ejerciciosApiViewModel.ejercicios.collectAsState()

    // Filtrar por equipo
    val ejerciciosFiltrados = ejercicios.filter { it.equipment.equals(equipo, ignoreCase = true) }

    if (ejerciciosFiltrados.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "No hay ejercicios para esta selección.",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(ejerciciosFiltrados) { ejercicio ->
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
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(ejercicio.gifUrl)
                                .crossfade(true)
                                .build(),
                            contentDescription = ejercicio.name,
                            contentScale = ContentScale.Crop,
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
