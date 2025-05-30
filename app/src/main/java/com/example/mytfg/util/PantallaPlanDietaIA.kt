package com.example.mytfg.util

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
fun PantallaPlanDietaIA(
    navHostController: NavHostController,
    planDieta: String
) {
    Scaffold(
        topBar = {
            TopBar(
                navHostController = navHostController,
                title = "Plan de dieta generado"
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Imagen de fondo
            Image(
                painter = painterResource(id = R.drawable.imagenia),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // Capa translúcida
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White.copy(alpha = 0.5f))
            )

            // Función auxiliar para validar contenido útil
            val tieneContenidoValido: (String) -> Boolean = { texto ->
                texto.lines().any { linea ->
                    val cleanLine = linea.trimStart('*', '+', ' ')
                    cleanLine.isNotBlank()
                }
            }

            // Contenido principal
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val partes = Regex("\\*\\*(.*?)\\*\\*").split(planDieta)
                val titulos = Regex("\\*\\*(.*?)\\*\\*").findAll(planDieta).map { it.groupValues[1] }.toList()

                var tituloIndex = 0
                partes.forEachIndexed { index, parte ->
                    if (parte.isNotBlank() && tieneContenidoValido(parte)) {
                        // Mostrar título correspondiente
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

                        // Mostrar contenido dentro de Card
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            colors = CardDefaults.cardColors(containerColor = NaranjaMuyClaro)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                parte.trim().lines().forEach { linea ->
                                    val cleanLine = linea.trimStart('*', '+', ' ')
                                    if (cleanLine.isNotBlank()) {
                                        Text(
                                            text = cleanLine,
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = Color.Black,
                                            modifier = Modifier.padding(bottom = 4.dp)
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
