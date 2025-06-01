/*
package com.example.mytfg.util

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
fun PantallaPlanEntrenamientoIA(
    navHostController: NavHostController,
    planEntrenamiento: String
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
            val partes = Regex("\\*\\*(.*?)\\*\\*").split(planEntrenamiento)
            val titulos = Regex("\\*\\*(.*?)\\*\\*").findAll(planEntrenamiento).map { it.groupValues[1] }.toList()

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
*/



package com.example.mytfg.util

import android.content.Intent
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
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import com.example.mytfg.componentes.BotonEstandar

@Composable
fun PantallaPlanEntrenamientoIA(
    navHostController: NavHostController,
    planEntrenamiento: String
) {
    val context = LocalContext.current

    Log.d("PlanEntrenamientoOriginal", "Contenido original del plan de entrenamiento:\n$planEntrenamiento")

    Scaffold(
        topBar = {
            TopBar(
                navHostController = navHostController,
                title = "Plan de entrenamiento generado"
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Image(
                painter = painterResource(id = R.drawable.imagenia),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White.copy(alpha = 0.5f))
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val regexDia = Regex("(?i)(lunes|martes|miércoles|jueves|viernes|sábado|domingo|día\\s*\\d+)")
                val matches = regexDia.findAll(planEntrenamiento)

                val bloques = mutableListOf<Pair<String, String>>()
                val indices = matches.map { it.range.first }.toList()

                for (i in indices.indices) {
                    val start = indices[i]
                    val end = if (i + 1 < indices.size) indices[i + 1] else planEntrenamiento.length
                    val titulo = matches.elementAt(i).value.trim().replaceFirstChar { it.uppercase() }
                    val contenido = planEntrenamiento.substring(start, end).removePrefix(titulo).trim()
                    bloques.add(titulo to contenido)
                }

                // Introducción antes del primer día
                val primerDiaRegex = Regex("(?i)\\b(lunes|martes|miércoles|jueves|viernes|sábado|domingo|día\\s*\\d+)\\b")
                val indicePrimerDia = primerDiaRegex.find(planEntrenamiento)?.range?.first ?: 0
                val introduccion = planEntrenamiento.substring(0, indicePrimerDia).trim()

                if (introduccion.isNotBlank()) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    ) {
                        Text(
                            text = "Entrenamiento semanal personalizado",
                            style = MaterialTheme.typography.headlineSmall,
                            color = Color.Black,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp)
                        )

                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = NaranjaMuyClaro)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                introduccion.lines().forEach { linea ->
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

                // Mostrar bloques por día
                bloques.forEach { (tituloDia, contenidoDia) ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp)
                    ) {
                        Text(
                            text = tituloDia,
                            style = MaterialTheme.typography.headlineSmall,
                            color = Color.Black,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp)
                        )

                        Divider(
                            color = Color.Gray,
                            thickness = 1.dp,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            colors = CardDefaults.cardColors(containerColor = NaranjaMuyClaro)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                contenidoDia.lines().forEach { linea ->
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

                Spacer(modifier = Modifier.height(24.dp))

                BotonEstandar(
                    texto = "Compartir plan entrenamiento",
                    onClick = {
                        val intent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(Intent.EXTRA_SUBJECT, "Mi plan de dieta personalizado")
                            putExtra(Intent.EXTRA_TEXT, planEntrenamiento)
                        }
                        context.startActivity(Intent.createChooser(intent, "Compartir plan con..."))
                    }
                )

                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}
