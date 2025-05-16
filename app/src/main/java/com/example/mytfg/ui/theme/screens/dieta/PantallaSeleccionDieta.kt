package com.example.mytfg.ui.theme.screens.dieta

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.mytfg.R
import com.example.mytfg.viewmodel.DietaViewModel

@Composable
fun PantallaSeleccionDieta(
    navHostController: NavHostController,
    viewModel: DietaViewModel = viewModel(),
    paddingValues: PaddingValues = PaddingValues(0.dp)
) {
    val opciones = listOf("Aumentar peso", "Reducir peso", "Tonificar", "Ganar masa muscular", "Mantener peso")
    val objetivoSeleccionado by viewModel.objetivoSeleccionado.collectAsState()

    val claveObjetivo = when (objetivoSeleccionado) {
        "Aumentar peso" -> "aumentar_peso"
        "Reducir peso" -> "reducir_peso"
        "Tonificar" -> "tonificar"
        "Ganar masa muscular" -> "ganar_masa_muscular"
        "Mantener peso" -> "mantener_peso"
        else -> null
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        Image(
            painter = painterResource(id = R.drawable.dietaensalada),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White.copy(alpha = 0.7f))
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 16.dp)
        ) {
            Text(
                text = "Selecciona tu objetivo",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            ) {
                opciones.forEach { opcion ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = objetivoSeleccionado == opcion,
                            onClick = { viewModel.seleccionarObjetivo(opcion) }
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = opcion,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { viewModel.seleccionarObjetivo(opcion) }
                        )
                    }
                }
            }

            Button(
                onClick = {
                    claveObjetivo?.let {
                        navHostController.navigate("PantallaDieta/$it")
                    }
                },
                enabled = claveObjetivo != null,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
                    .navigationBarsPadding()
            ) {
                Text("Continuar")
            }
        }
    }
}
