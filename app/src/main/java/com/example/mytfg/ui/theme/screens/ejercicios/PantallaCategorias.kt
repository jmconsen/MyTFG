package com.example.mytfg.ui.theme.screens.ejercicios

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.mytfg.viewmodel.CategoriasViewModel
import com.example.mytfg.util.getApiBodyPart

@Composable
fun PantallaCategorias(
    navHostController: NavHostController,
    viewModel: CategoriasViewModel = viewModel(),
    paddingValues: PaddingValues = PaddingValues()
) {
    val categorias by viewModel.categorias.collectAsState()

    // Equipos por defecto para las categorías normales
    val equiposPorDefecto = listOf(
        "body weight", "dumbbell", "barbell", "kettlebell", "cable", "machine", "band", "medicine ball"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            categorias.forEach { categoria ->
                // Si la categoría es "Espalda", solo muestra "barbell" y "cable"
                val equipos = when (categoria.nombre.lowercase()) {
                    "espalda" -> listOf("barbell", "cable")
                    "piernas superiores" -> listOf("body weight", "barbell")
                    "piernas inferiores" -> listOf("body weight", "dumbbell", "barbell")
                    "torso" -> listOf("body weight", "barbell", "medicine ball")
                    else -> equiposPorDefecto
                }

                item {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = categoria.imagenResId),
                            contentDescription = categoria.nombre,
                            modifier = Modifier.size(128.dp).aspectRatio(1f).padding(end = 16.dp)
                        )
                        Text(
                            text = categoria.nombre,
                            style = MaterialTheme.typography.titleLarge, // Usa un estilo más grande
                            fontSize = 24.sp, // Tamaño de fuente aumentado
                            modifier = Modifier.padding(top = 20.dp, bottom = 12.dp)
                        )
                    }
                }


                items(equipos) { equipo ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 6.dp)
                            .clickable {
                                navHostController.navigate(
                                    "PantallaEjercicios/${getApiBodyPart(categoria.nombre)}/$equipo"
                                )
                            }
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = equipo.replaceFirstChar { it.uppercase() },
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    }
                }
            }
        }
    }
}
