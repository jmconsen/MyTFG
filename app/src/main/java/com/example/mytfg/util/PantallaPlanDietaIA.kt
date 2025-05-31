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
import android.util.Log

@Composable
fun PantallaPlanDietaIA(
    navHostController: NavHostController,
    planDieta: String
) {

    // Mostrar el texto original sin procesar
    Log.d("PlanDietaOriginal", "Contenido original del plan de dieta:\n$planDieta")

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
                val matches = regexDia.findAll(planDieta)

                val bloques = mutableListOf<Pair<String, String>>()
                val indices = matches.map { it.range.first }.toList()

                for (i in indices.indices) {
                    val start = indices[i]
                    val end = if (i + 1 < indices.size) indices[i + 1] else planDieta.length
                    val titulo = matches.elementAt(i).value.trim().replaceFirstChar { it.uppercase() }
                    val contenido = planDieta.substring(start, end).removePrefix(titulo).trim()
                    bloques.add(titulo to contenido)
                }

                // Detectar contenido antes del primer día
                val primerDiaRegex = Regex("(?i)\\b(lunes|martes|miércoles|jueves|viernes|sábado|domingo|día\\s*\\d+)\\b")
                val indicePrimerDia = primerDiaRegex.find(planDieta)?.range?.first ?: 0
                val introduccion = planDieta.substring(0, indicePrimerDia).trim()

                if (introduccion.isNotBlank()) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    ) {
                        Text(
                            text = "Dieta semanal personalizada",
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



                /*
                val TAG = "PlanDietaDebug"

                bloques.forEach { (tituloDia, contenidoDia) ->
                    Log.d(TAG, "Día: $tituloDia")

                    val regexComida = Regex("(?i)(Desayuno|Comida|Cena):?")
                    val secciones = contenidoDia.split(regexComida)
                    val etiquetas = regexComida.findAll(contenidoDia).map { it.value.trim(':', ' ', '\n') }.toList()

                    val contenidoPorComida = mutableMapOf<String, String>()
                    etiquetas.forEachIndexed { i, etiqueta ->
                        if (i < secciones.size - 1) {
                            contenidoPorComida[etiqueta.lowercase()] = secciones[i + 1].trim()
                        }
                    }

                    listOf("Desayuno", "Comida", "Cena").forEach { comida ->
                        val contenido = contenidoPorComida[comida.lowercase()]
                        if (!contenido.isNullOrBlank()) {
                            Log.d(TAG, "  $comida:")
                            contenido.lines().forEach { linea ->
                                val cleanLine = linea.trimStart('*', '+', ' ')
                                if (cleanLine.isNotBlank()) {
                                    Log.d(TAG, "    - $cleanLine")
                                }
                            }
                        }
                    }
                }

                 */


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

                        val comidas = listOf("Desayuno", "Comida", "Cena")
                        val contenidoPorComida = mutableMapOf<String, String>()

                        val regexComida = Regex("(?i)(Desayuno|Comida|Cena):?")
                        val secciones = contenidoDia.split(regexComida)
                        val etiquetas = regexComida.findAll(contenidoDia).map { it.value.trim(':', ' ', '\n') }.toList()

                        etiquetas.forEachIndexed { i, etiqueta ->
                            if (i < secciones.size - 1) {
                                contenidoPorComida[etiqueta.lowercase()] = secciones[i + 1].trim()
                            }
                        }

                        comidas.forEach { comida ->
                            val contenido = contenidoPorComida[comida.lowercase()]
                            if (!contenido.isNullOrBlank()) {
                                Text(
                                    text = comida,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = Color.DarkGray,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 6.dp)
                                )

                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp),
                                    colors = CardDefaults.cardColors(containerColor = NaranjaMuyClaro)
                                ) {
                                    Column(modifier = Modifier.padding(16.dp)) {
                                        contenido.lines().forEach { linea ->
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
    }

}



/*
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

            // Contenido principal con scroll
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val encabezadosDiaRegex = Regex("(?i)(?=\\b(?:lunes|martes|miércoles|jueves|viernes|sábado|domingo|día\\s*\\d+)\\b)")
                val bloquesPorDia = planDieta.replace("\r\n", "\n").replace("\r", "\n")
                    .split(encabezadosDiaRegex)

                val encabezados = encabezadosDiaRegex.findAll(planDieta)
                    .map { it.value.trim().replaceFirstChar { c -> c.uppercase() } }
                    .toList()

                bloquesPorDia.forEachIndexed { index, bloque ->
                    if (index < encabezados.size) {
                        val tituloDia = encabezados[index]

                        // Sección por día
                        Divider(
                            color = Color.Gray,
                            thickness = 1.dp,
                            modifier = Modifier.padding(vertical = 12.dp)
                        )

                        Text(
                            text = tituloDia,
                            style = MaterialTheme.typography.headlineSmall,
                            color = Color.Black,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp)
                        )

                        // Extraer desayuno, comida y cena
                        val comidas = listOf("Desayuno", "Comida", "Cena")
                        val contenidoPorComida = mutableMapOf<String, String>()

                        val regexComida = Regex("(?i)(Desayuno|Comida|Cena):?")
                        val secciones = bloque.split(regexComida)
                        val etiquetas = regexComida.findAll(bloque).map { it.value.trim(':', ' ', '\n') }.toList()

                        etiquetas.forEachIndexed { i, etiqueta ->
                            if (i < secciones.size - 1) {
                                contenidoPorComida[etiqueta.lowercase()] = secciones[i + 1].trim()
                            }
                        }

                        comidas.forEach { comida ->
                            val contenido = contenidoPorComida[comida.lowercase()]
                            if (!contenido.isNullOrBlank()) {
                                Text(
                                    text = comida,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = Color.DarkGray,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 6.dp)
                                )

                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp),
                                    colors = CardDefaults.cardColors(containerColor = NaranjaMuyClaro)
                                ) {
                                    Column(modifier = Modifier.padding(16.dp)) {
                                        contenido.lines().forEach { linea ->
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
    }
}
*/




/*
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
*/