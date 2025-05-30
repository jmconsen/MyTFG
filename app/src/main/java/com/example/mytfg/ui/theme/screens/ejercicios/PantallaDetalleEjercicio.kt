package com.example.mytfg.ui.theme.screens.ejercicios

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.mytfg.model.EjercicioApi
import com.example.mytfg.viewmodel.EjerciciosApiViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mytfg.R
import com.example.mytfg.componentes.TopBar
import com.example.mytfg.viewmodel.EjerciciosApiViewModelFactory
import com.example.mytfg.componentes.BotonEstandar

@Composable
fun PantallaDetalleEjercicio(
    navHostController: NavHostController,
    ejercicioId: String,
    paddingValues: PaddingValues = PaddingValues()
) {
    val viewModel: EjerciciosApiViewModel = viewModel(factory = EjerciciosApiViewModelFactory())
    var ejercicio by remember { mutableStateOf<EjercicioApi?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(ejercicioId) {
        isLoading = true
        ejercicio = viewModel.cargarEjercicioPorId(ejercicioId)
        isLoading = false
    }

    if (isLoading) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    if (ejercicio == null) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Ejercicio no encontrado")
        }
        return
    }

    val clipboardManager = LocalClipboardManager.current

    Scaffold(
        topBar = {
            TopBar(
                navHostController = navHostController,
                title = "Ejercicio: ${ejercicioId.replace('_', ' ').replaceFirstChar { it.uppercase() }}"
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
                painter = painterResource(id = R.drawable.detallejercicios),
                contentDescription = "Fondo de ejercicio",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // Capa blanca translúcida
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White.copy(alpha = 0.7f))
            )

            // Contenido principal
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = ejercicio!!.gifUrl,
                    contentDescription = "Animación del ejercicio",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                        .clip(RoundedCornerShape(18.dp))
                        .background(Color.White)
                )

                Spacer(modifier = Modifier.height(18.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text(
                            "Grupo muscular: ${ejercicio!!.target.replaceFirstChar { it.uppercase() }}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text("Parte del cuerpo: ${ejercicio!!.bodyPart.replaceFirstChar { it.uppercase() }}")
                        Text("Equipo: ${ejercicio!!.equipment.replaceFirstChar { it.uppercase() }}")
                    }
                }

                Spacer(modifier = Modifier.height(22.dp))

                Text(
                    "Las instrucciones están en inglés porque la fuente de datos solo las proporciona en ese idioma.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    "Instrucciones",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(8.dp))

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp)),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(Modifier.padding(16.dp)) {
                        BotonEstandar(
                            texto = "📋 Copiar instrucciones",
                            onClick = {
                                val todas = ejercicio!!.instructions
                                    ?.joinToString("\n") { "- $it" } ?: ""
                                clipboardManager.setText(AnnotatedString(todas))
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp)
                        )

                        ejercicio!!.instructions?.forEachIndexed { idx, instruccion ->
                            Text(
                                "${idx + 1}. $instruccion",
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(bottom = 6.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
    }
}
