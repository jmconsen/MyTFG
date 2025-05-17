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
import com.example.mytfg.componentes.BotonEstandar
import com.example.mytfg.viewmodel.DietaViewModel

@Composable
fun PantallaSeleccionDieta(
    navHostController: NavHostController,
    viewModel: DietaViewModel = viewModel(),
    paddingValues: PaddingValues = PaddingValues(0.dp)
) {
    // Lista con texto para mostrar y clave para Firestore
    val opciones = listOf(
        "Aumentar peso" to "aumentar_peso",
        "Reducir peso" to "reducir_peso",
        "Tonificar" to "tonificar",
        "Mantener peso" to "mantener_peso"
    )
    val objetivoSeleccionado by viewModel.objetivoSeleccionado.collectAsState()

    // Obtenemos la clave Firestore del objetivo seleccionado
    val claveObjetivo = opciones.find { it.first == objetivoSeleccionado }?.second

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
                opciones.forEach { (texto, _) ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = objetivoSeleccionado == texto,
                            onClick = { viewModel.seleccionarObjetivo(texto) }
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = texto,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { viewModel.seleccionarObjetivo(texto) }
                        )
                    }
                }
            }

            BotonEstandar(
                texto = "Continuar",
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
            )
        }
    }
}
