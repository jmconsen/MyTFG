package com.example.mytfg.componentes

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.mytfg.ui.theme.Blanco
import com.example.mytfg.ui.theme.Naranja

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    navHostController: NavHostController,
    title: String = "Perfil"
) {
    TopAppBar(
        title = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = title,
                    color = Color.White // Texto blanco
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = {
                navHostController.popBackStack()
            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Atrás",
                    tint = Color.White // Icono blanco
                )
            }
        },
        actions = {
            IconButton(onClick = {
                navHostController.navigate("PantallaMenu")
            }) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Menú",
                    tint = Color.White // Icono blanco
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Naranja,
            titleContentColor = Color.White,
            navigationIconContentColor = Color.White,
            actionIconContentColor = Color.White
        )
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarConUsuario(
    userName: String,
    avatarUrl: String?,
    onProfileClick: () -> Unit
) {
    val defaultAvatar = "https://cdn-icons-png.flaticon.com/512/149/149071.png"

    TopAppBar(
        title = {
            Text(
                text = "Bienvenido, $userName",
                color = Blanco
            )
        },
        actions = {
            IconButton(onClick = onProfileClick) {
                AsyncImage(
                    model = avatarUrl ?: defaultAvatar,
                    contentDescription = "Avatar de usuario",
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Naranja
        )
    )
}




/*
package com.example.mytfg.componentes

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.mytfg.ui.theme.Naranja

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    navHostController: NavHostController,
    title: String = "Perfil"
) {
    TopAppBar(
        title = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(title)
            }
        },
        navigationIcon = {
            IconButton(onClick = {
                navHostController.popBackStack("PantallaMenu", inclusive = false)
            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Atrás"
                )
            }
        },
        actions = {
            IconButton(onClick = {
                navHostController.navigate("PantallaMenu")
            }) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Menú"
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Naranja
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarConUsuario(
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
            containerColor = Naranja
        )
    )
}

 */




/*


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

 */