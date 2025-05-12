package com.example.mytfg.componentes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    userName: String,
    avatarUrl: String,
    onProfileClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = "Bienvenido, $userName",
                color = Color.White // Texto blanco sobre fondo naranja
            )
        },
        actions = {
            IconButton(onClick = onProfileClick) {
                AsyncImage(
                    model = avatarUrl,
                    contentDescription = "Avatar de usuario",
                    modifier = Modifier
                        .size(44.dp) // Más grande
                        .clip(CircleShape)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFFFF9800) // Naranja
        )
    )
}