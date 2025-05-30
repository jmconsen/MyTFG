package com.example.mytfg.componentes

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.mytfg.ui.theme.Naranja
import com.example.mytfg.ui.theme.NaranjaOscuro

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarConUsuarioOLD(
    userName: String,
    avatarUrl: String,
    onProfileClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = "Bienvenido, $userName",
                color = Color.White
            )
        },
        actions = {
            IconButton(onClick = onProfileClick) {
                AsyncImage(
                    model = avatarUrl,
                    contentDescription = "Avatar de usuario",
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = NaranjaOscuro
        )
    )
}
