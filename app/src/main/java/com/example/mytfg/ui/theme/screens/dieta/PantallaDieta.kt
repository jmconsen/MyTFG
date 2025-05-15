package com.example.mytfg.ui.theme.screens.dieta

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.mytfg.viewmodel.DietaViewModel

@Composable
fun PantallaDieta(
    navHostController: NavHostController,
    objetivo: String,
    viewModel: DietaViewModel = viewModel()
) {
    val contenidoDieta by viewModel.contenidoDieta.collectAsState()

    // Cargar la dieta al entrar en la pantalla
    LaunchedEffect(objetivo) {
        viewModel.cargarDieta(objetivo)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Tu plan nutricional",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            contentAlignment = Alignment.TopStart
        ) {
            Text(
                text = contenidoDieta ?: "Objetivo no válido",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.fillMaxWidth(),
                softWrap = true
            )
        }

        Button(
            onClick = { navHostController.navigate("PantallaMenu") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp)
        ) {
            Text("Volver al menú principal")
        }
    }
}
