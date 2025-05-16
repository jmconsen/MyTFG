package com.example.mytfg.ui.theme.screens.ejercicios

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
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

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues), // Respeta TopBar y BottomBar del Scaffold global
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        categorias.forEach { categoria ->
            item {
                Text(
                    text = categoria.nombre,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
                )
            }
            items(categoria.niveles) { nivel ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp)
                        .clickable {
                            navHostController.navigate(
                                "PantallaEjercicios/${getApiBodyPart(categoria.nombre)}/${nivel.nivel}"
                            )
                        }
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = nivel.imagenResId),
                            contentDescription = "${categoria.nombre} ${nivel.nivel}",
                            modifier = Modifier.size(80.dp)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = nivel.nivel,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }
        }
    }
}
