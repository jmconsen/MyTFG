package com.example.mytfg.ui.theme.screens.dieta

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.mytfg.componentes.BotonEstandar
import com.example.mytfg.componentes.TopBar
import com.example.mytfg.viewmodel.DietaViewModel

@Composable
fun PantallaDieta(
    navHostController: NavHostController,
    objetivoClave: String,
    viewModel: DietaViewModel = viewModel()
) {
    val contenidoDieta by viewModel.contenidoDieta.collectAsState()

    LaunchedEffect(objetivoClave) {
        viewModel.cargarDieta(objetivoClave)
    }

    val secciones = contenidoDieta?.split("\n\n") ?: listOf("Cargando dieta...")

    Scaffold(
        topBar = {
            TopBar(
                navHostController = navHostController,
                title = "Dieta: ${objetivoClave.replace('_', ' ').replaceFirstChar { it.uppercase() }}"
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text(
                text = objetivoClave.replace('_', ' ').replaceFirstChar { it.uppercase() },
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            Surface(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                tonalElevation = 4.dp,
                shape = MaterialTheme.shapes.medium
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp)
                ) {
                    secciones.forEach { seccion ->
                        val partes = seccion.split(":", limit = 2)
                        if (partes.size == 2) {
                            val titulo = partes[0].trim()
                            val contenido = partes[1].trim()
                            Text(
                                text = titulo,
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
                            )
                            Text(
                                text = contenido,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                        } else {
                            Text(
                                text = seccion.trim(),
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            BotonEstandar(
                texto = "Volver al menú principal",
                onClick = { navHostController.navigate("PantallaMenu") },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
