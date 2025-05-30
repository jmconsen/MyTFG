package com.example.mytfg.ui.theme.screens.ia

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mytfg.R
import com.example.mytfg.componentes.TopBar
import com.example.mytfg.ui.theme.NaranjaMuyClaro

@Composable
fun PantallaPlanGenerado(
    navHostController: NavHostController,
    plan: String
) {
    Scaffold(
        topBar = {
            TopBar(
                navHostController = navHostController,
                title = "Plan de Entrenamiento"
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Fondo con imagen
            Image(
                painter = painterResource(id = R.drawable.imagenia),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // Capa blanca translúcida
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White.copy(alpha = 0.5f))
            )

            // Función para validar contenido útil (líneas que no sean solo símbolos)
            val tieneContenidoValido: (String) -> Boolean = { texto ->
                texto.lines().any { linea ->
                    val cleanLine = linea.trimStart('*', '+', ' ')
                    cleanLine.isNotBlank()
                }
            }

            // Dividir texto en secciones por títulos **Titulo**
            val partes = Regex("\\*\\*(.*?)\\*\\*").split(plan)
            val titulos = Regex("\\*\\*(.*?)\\*\\*").findAll(plan).map { it.groupValues[1] }.toList()

            var tituloIndex = 0

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                partes.forEachIndexed { index, parte ->
                    val contenido = parte.trim()
                    if (contenido.isNotBlank() && tieneContenidoValido(contenido)) {
                        // Mostrar título si no es la primera parte (antes del primer título)
                        if (index > 0 && tituloIndex < titulos.size) {
                            Text(
                                text = titulos[tituloIndex],
                                style = MaterialTheme.typography.titleLarge,
                                color = Color.Black,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                            )
                            tituloIndex++
                        }

                        // Mostrar contenido en tarjeta naranja
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp),
                            colors = CardDefaults.cardColors(containerColor = NaranjaMuyClaro),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                contenido.lines().forEach { linea ->
                                    val cleanLine = linea.trimStart('*', '+', ' ')
                                    if (cleanLine.isNotBlank()) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            modifier = Modifier.padding(bottom = 4.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.FitnessCenter,
                                                contentDescription = null,
                                                tint = MaterialTheme.colorScheme.primary,
                                                modifier = Modifier.size(20.dp)
                                            )
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Text(
                                                text = cleanLine,
                                                style = MaterialTheme.typography.bodyMedium,
                                                color = Color.Black
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
