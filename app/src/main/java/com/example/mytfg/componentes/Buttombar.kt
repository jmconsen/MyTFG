package com.example.mytfg.componentes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mytfg.ui.theme.Blanco
import com.example.mytfg.ui.theme.Naranja
import com.example.mytfg.ui.theme.NaranjaOscuro

@Composable
fun BottomBar(
    onExit: () -> Unit
) {
    BottomAppBar(
        containerColor = NaranjaOscuro,
        contentColor = Blanco,
        modifier = Modifier.height(88.dp) // Altura estándar
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            IconButton(
                onClick = onExit,
                modifier = Modifier.align(Alignment.Center)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                    contentDescription = "Cerrar sesión",
                    tint = Blanco,
                    modifier = Modifier.size(28.dp) // Ajustar el tamaño del icono
                )
            }
        }
    }
}


@Composable
fun BottomBarCopyright() {
    BottomAppBar(
        containerColor = NaranjaOscuro,
        contentColor = Blanco,
        modifier = Modifier.height(88.dp) // Ajusta la altura aquí
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(),
                //.padding(vertical = 36.dp), // Menos padding vertical
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "© 2025 Copyright MyFit",
                fontSize = 12.sp,
                color = Blanco
            )
        }
    }
}
