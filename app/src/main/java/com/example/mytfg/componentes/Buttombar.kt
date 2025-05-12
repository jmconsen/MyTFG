package com.example.mytfg.componentes

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BottomBar(
    onBack: () -> Unit,
    onExit: () -> Unit
) {
    BottomAppBar(
        containerColor = Color(0xFFFF9800), // Naranja
        contentColor = Color.White
    ) {
        // Centrado horizontal y vertical
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp), // Más alto para iconos grandes
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier.size(56.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Atrás",
                    tint = Color.White,
                    modifier = Modifier.size(36.dp) // Icono más grande
                )
            }
            Spacer(modifier = Modifier.width(32.dp))
            IconButton(
                onClick = onExit,
                modifier = Modifier.size(56.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                    contentDescription = "Salir",
                    tint = Color.White,
                    modifier = Modifier.size(36.dp) // Icono más grande
                )
            }
        }
    }
}
