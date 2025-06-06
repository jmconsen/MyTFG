package com.example.mytfg.ui.theme.screens.ejercicios

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cable
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.SportsBasketball
import androidx.compose.material.icons.filled.SportsHandball
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.mytfg.R
import com.example.mytfg.componentes.TopBar
import com.example.mytfg.ui.theme.Blanco
import com.example.mytfg.ui.theme.NaranjaClaro
import com.example.mytfg.ui.theme.NaranjaMuyClaro
import com.example.mytfg.viewmodel.CategoriasViewModel
import com.example.mytfg.util.getApiBodyPart
import com.example.mytfg.util.traducirEquipo
import androidx.compose.ui.draw.clip

@Composable
fun PantallaCategorias(
    navHostController: NavHostController,
    viewModel: CategoriasViewModel = viewModel(),
    paddingValues: PaddingValues = PaddingValues()
) {
    val categorias by viewModel.categorias.collectAsState()
    val equiposPorDefecto = listOf(
        "body weight",
        "dumbbell",
        "barbell",
        "kettlebell",
        "cable",
        "machine",
        "band",
        "medicine ball"
    )

    val expandedStates = remember { mutableStateMapOf<String, Boolean>() }

    Scaffold(
        topBar = {
            TopBar(
                navHostController = navHostController,
                title = "Categoría Ejercicios"
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
                painter = painterResource(id = R.drawable.categorias),
                contentDescription = "Fondo de Categorías",
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
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(categorias.sortedBy { it.nombre }) { categoria -> // Ordenar categorías alfabéticamente
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp, horizontal = 16.dp)
                            .clickable {
                                expandedStates[categoria.nombre] =
                                    !(expandedStates[categoria.nombre] ?: false)
                            },
                        colors = CardDefaults.cardColors(containerColor = NaranjaClaro),
                        elevation = CardDefaults.cardElevation(4.dp),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = categoria.imagenResId),
                                contentDescription = categoria.nombre,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    //.size(96.dp)
                                    //.aspectRatio(1f)
                                    .width(110.dp) // Ancho
                                    .height(80.dp) // Alto
                                    .padding(start = 8.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
                                    .clip(RoundedCornerShape(12.dp))
                            )
                            Text(
                                text = categoria.nombre,
                                //style = MaterialTheme.typography.titleLarge,
                                fontSize = 18.sp
                            )
                        }
                    }

                    val expanded = expandedStates[categoria.nombre] ?: false
                    if (expanded) {
                        categoria.equipos.sorted()
                            .forEach { equipo -> // Equipos ordenados alfabéticamente

                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        //.padding(vertical = 8.dp, horizontal = 16.dp)
                                        .padding(
                                            start = 64.dp,
                                            top = 4.dp,
                                            end = 16.dp,
                                            bottom = 4.dp
                                        )
                                        .clickable {
                                            navHostController.navigate(
                                                "PantallaEjercicios/${getApiBodyPart(categoria.nombre)}/$equipo"
                                            )
                                        },
                                    //colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                                    colors = CardDefaults.cardColors(containerColor = NaranjaMuyClaro),
                                    elevation = CardDefaults.cardElevation(4.dp),
                                    shape = RoundedCornerShape(16.dp)
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            val icon = when (equipo.lowercase()) {
                                                "body weight" -> Icons.Default.Person
                                                "barbell" -> Icons.Default.FitnessCenter
                                                "dumbbell" -> Icons.Default.SportsHandball
                                                "cable" -> Icons.Default.Cable
                                                "medicine ball" -> Icons.Default.SportsBasketball
                                                else -> Icons.Default.Help // Ícono por defecto
                                            }

                                            Icon(
                                                imageVector = icon,
                                                contentDescription = null,
                                                modifier = Modifier.padding(end = 8.dp)
                                            )
                                            Text(
                                                text = traducirEquipo(equipo),
                                                fontSize = 16.sp,
                                                style = MaterialTheme.typography.bodyMedium
                                            )
                                        }
                                    }
                                }
                            }
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(64.dp)) // Espacio adicional al final
                }
            }
        }
    }
}
