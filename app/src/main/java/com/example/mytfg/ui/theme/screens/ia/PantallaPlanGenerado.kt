package com.example.mytfg.ui.theme.screens.ia

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mytfg.componentes.TopBar

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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text(
                text = plan,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
