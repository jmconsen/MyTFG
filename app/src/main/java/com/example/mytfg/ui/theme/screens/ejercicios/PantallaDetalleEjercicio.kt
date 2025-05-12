package com.example.mytfg.ui.theme.screens.ejercicios

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.mytfg.viewmodel.EjerciciosApiViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaDetalleEjercicio(
    navHostController: NavHostController,
    ejercicioId: String,
    viewModel: EjerciciosApiViewModel = viewModel()
) {
    val ejercicio = viewModel.ejercicios.collectAsState().value.find { it.id == ejercicioId }

    if (ejercicio == null) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Ejercicio no encontrado")
        }
        return
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(ejercicio.name.replaceFirstChar { it.uppercase() }) },
                navigationIcon = {
                    IconButton(onClick = { navHostController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Atrás"
                        )
                    }
                }
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.08f),
                            MaterialTheme.colorScheme.background
                        )
                    )
                )
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // GIF animado del ejercicio
            AsyncImage(
                model = ejercicio.gifUrl,
                contentDescription = "Animación del ejercicio",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .clip(RoundedCornerShape(18.dp))
                    .background(Color.White)
            )

            Spacer(modifier = Modifier.height(18.dp))

            // Información principal
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text(
                        "Grupo muscular: ${ejercicio.target.replaceFirstChar { it.uppercase() }}",
                        fontWeight = FontWeight.Bold
                    )
                    Text("Parte del cuerpo: ${ejercicio.bodyPart.replaceFirstChar { it.uppercase() }}")
                    Text("Equipo: ${ejercicio.equipment.replaceFirstChar { it.uppercase() }}")
                }
            }

            Spacer(modifier = Modifier.height(22.dp))

            // Instrucciones
            Text(
                "Instrucciones",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp)),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(Modifier.padding(16.dp)) {
                    // Si instructions es una lista de String
                    ejercicio.instructions?.forEachIndexed { idx, instruccion ->
                        Text(
                            "${idx + 1}. $instruccion",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(bottom = 6.dp)
                        )
                    }
                }
            }
        }
    }
}
