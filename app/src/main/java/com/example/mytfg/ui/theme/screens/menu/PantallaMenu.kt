package com.example.mytfg.ui.theme.screens.menu

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mytfg.R
import com.example.mytfg.componentes.BotonEstandar
import com.example.mytfg.ui.theme.GrisOscuro2

@Composable
fun PantallaMenu(navHostController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // 1. Imagen de fondo
        Image(
            painter = painterResource(id = R.drawable.image5), // tu imagen en drawable
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // 2. Capa blanca translúcida encima
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White.copy(alpha = 0.7f))
        )

        // 3. Contenido del menú
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(64.dp)) // Espacio superior para separar del TopBar global

            // Tarjeta moderna para el menú
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .clip(RoundedCornerShape(24.dp))
                    //.background(Color.White.copy(alpha = 0.95f))
                    .padding(vertical = 32.dp, horizontal = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Menú Principal",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = GrisOscuro2,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )

                    BotonEstandar(
                        texto = "Entrenamiento con IA",
                        onClick = { navHostController.navigate("PantallaCategorias") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    )

                    BotonEstandar(
                        texto = "Ejercicios",
                        onClick = { navHostController.navigate("PantallaCategorias") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    )

                    BotonEstandar(
                        texto = "Perfil",
                        onClick = { navHostController.navigate("Perfil") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    )

                    BotonEstandar(
                        texto = "Dietas",
                        onClick = { navHostController.navigate("PantallaSeleccionDieta") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    )
                }
            }
        }
    }
}
