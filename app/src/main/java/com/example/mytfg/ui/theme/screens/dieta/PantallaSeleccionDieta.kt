package com.example.mytfg.ui.theme.screens.dieta

import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.mytfg.viewmodel.DietaViewModel

@Composable
fun PantallaSeleccionDieta(
    navHostController: NavHostController,
    viewModel: DietaViewModel = viewModel()
) {
    val opciones = listOf("Aumentar peso", "Reducir peso", "Tonificar", "Mantener peso")
    val objetivoSeleccionado by viewModel.objetivoSeleccionado.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Selecciona tu objetivo",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        opciones.forEach { opcion ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = objetivoSeleccionado == opcion,
                        onClick = { viewModel.seleccionarObjetivo(opcion) }
                    )
                    .padding(vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = objetivoSeleccionado == opcion,
                    onClick = { viewModel.seleccionarObjetivo(opcion) }
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = opcion,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp)
                        .selectable(
                            selected = objetivoSeleccionado == opcion,
                            onClick = { viewModel.seleccionarObjetivo(opcion) }
                        )
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                objetivoSeleccionado?.let {
                    val objetivoFormateado = it.replace(" ", "_").lowercase()
                    navHostController.navigate("PantallaDieta/$objetivoFormateado")
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            enabled = objetivoSeleccionado != null
        ) {
            Text("Continuar", style = MaterialTheme.typography.labelLarge)
        }
    }
}
