package com.example.mytfg.ui.theme.screens.ejercicios

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.mytfg.viewmodel.CategoriasViewModel
import com.example.mytfg.util.getApiBodyPart
import com.example.mytfg.util.traducirEquipo

@Composable
fun PantallaCategorias(
    navHostController: NavHostController,
    viewModel: CategoriasViewModel = viewModel(),
    paddingValues: PaddingValues = PaddingValues()
) {
    val categorias by viewModel.categorias.collectAsState()
    val equiposPorDefecto = listOf(
        "body weight", "dumbbell", "barbell", "kettlebell", "cable", "machine", "band", "medicine ball"
    )

    // Guarda el índice (o nombre) de las categorías expandidas
    val expandedStates = remember { mutableStateMapOf<String, Boolean>() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(categorias) { categoria ->
                val equipos = when (categoria.nombre.lowercase()) {
                    "espalda" -> listOf("barbell", "cable")
                    "piernas superiores" -> listOf("body weight", "barbell")
                    "piernas inferiores" -> listOf("body weight", "dumbbell", "barbell")
                    "torso" -> listOf("body weight", "barbell", "medicine ball")
                    "brazos" -> listOf("barbell")
                    "cardio" -> listOf("body weight", "dumbbell")
                    "antebrazo" -> listOf("dumbbell", "barbell", "cable")
                    else -> equiposPorDefecto
                }
                val expanded = expandedStates[categoria.nombre] ?: false

                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                expandedStates[categoria.nombre] = !expanded
                            }
                            .padding(vertical = 4.dp, horizontal = 12.dp)
                    ) {
                        Image(
                            painter = painterResource(id = categoria.imagenResId),
                            contentDescription = categoria.nombre,
                            modifier = Modifier.size(96.dp).aspectRatio(1f).padding(end = 16.dp)
                        )
                        Text(
                            text = categoria.nombre,
                            style = MaterialTheme.typography.titleLarge,
                            fontSize = 26.sp,
                            modifier = Modifier.padding(vertical = 14.dp)
                        )
                    }
                    if (expanded) {
                        equipos.forEach { equipo ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 32.dp, vertical = 4.dp)

                                    .clickable {
                                        navHostController.navigate(
                                            "PantallaEjercicios/${getApiBodyPart(categoria.nombre)}/$equipo"
                                        )
                                    },
                                colors = CardDefaults.cardColors(containerColor = Color.Black),
                                shape = RoundedCornerShape(16.dp)
                            ) {
                                Row(
                                    modifier = Modifier.padding(16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = traducirEquipo(equipo),
                                        style = MaterialTheme.typography.titleMedium,
                                        color = Color.White
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
