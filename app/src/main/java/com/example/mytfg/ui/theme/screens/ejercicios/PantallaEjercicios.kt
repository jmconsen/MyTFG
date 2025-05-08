package com.example.mytfg.ui.theme.screens.ejercicios

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.mytfg.viewmodel.EjerciciosApiViewModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PantallaEjercicios(
    navHostController: NavHostController,
    viewModel: EjerciciosApiViewModel = viewModel()
) {
    val ejercicios by viewModel.ejercicios.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.cargarEjercicios()
    }

    if (ejercicios.isEmpty()) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Cargando ejercicios o no hay ejercicios disponibles.")
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(ejercicios) { ejercicio ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = ejercicio.name)
                        Text(text = "Parte del cuerpo: ${ejercicio.bodyPart}")
                        Text(text = "Equipo: ${ejercicio.equipment}")
                        Text(text = "Objetivo: ${ejercicio.target}")
                    }
                }
            }
        }
    }
}
