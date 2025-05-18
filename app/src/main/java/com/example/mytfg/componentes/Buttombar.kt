package com.example.mytfg.componentes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
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

@Composable
fun BottomBar(
    onExit: () -> Unit
) {
    BottomAppBar(
        containerColor = Naranja,
        contentColor = Blanco,
        modifier = Modifier.height(88.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(),
                //.height(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    //.padding(end = 12.dp)
                    .clickable { onExit() },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                    contentDescription = "Cerrar sesión",
                    tint = Blanco,
                    modifier = Modifier.size(24.dp)
                )
                /*
                Text(
                    text = "Cerrar sesión",
                    color = Blanco,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center
                )
                */

            }
        }
    }
}

@Composable
fun BottomBarCopyright() {
    BottomAppBar(
        containerColor = Naranja,
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






/*
package com.example.mytfg.componentes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
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


@Composable
fun BottomBar(
    //onBack: () -> Unit,
    onExit: () -> Unit
) {
    BottomAppBar(
        containerColor = Naranja,
        contentColor = Blanco
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(24.dp),
            contentAlignment = Alignment.Center
        ) {

            /*
            // Botón atrás
            Column(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 12.dp)
                    .clickable { onBack() },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Atrás",
                    tint = Blanco,
                    modifier = Modifier.size(36.dp)
                )
                Text(
                    text = "Atrás",
                    color = Blanco,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center
                )
            }
            */

            // Botón de cerrar sesión
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(end = 12.dp)
                    .clickable { onExit() },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                    contentDescription = "Cerrar sesión",
                    tint = Blanco,
                    modifier = Modifier.size(36.dp)
                )
                Text(
                    text = "Cerrar sesión",
                    color = Blanco,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
*/