package com.example.mytfg.ui.theme.screens.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mytfg.componentes.BotonEstandar
import com.example.mytfg.ui.theme.FondoPantallas
import com.example.mytfg.ui.theme.GrisOscuro2

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaMenu(navHostController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(colors = FondoPantallas)
            )
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Barra superior moderna
            TopAppBar(
                title = {
                    Text(
                        text = "Bienvenido",
                        fontSize = 22.sp,
                        color = GrisOscuro2
                    )
                },
                actions = {
                    Icon(
                        imageVector = Icons.Filled.AccountCircle,
                        contentDescription = "Perfil",
                        tint = GrisOscuro2,
                        modifier = Modifier
                            .size(36.dp)
                            .padding(end = 16.dp)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Tarjeta moderna para el menú
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color.White.copy(alpha = 0.95f))
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
                        onClick = { navHostController.navigate("PantallaFacturasEmitidas") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    )

                    BotonEstandar(
                        texto = "Ejercicios",
                        onClick = { navHostController.navigate("PantallaEjercicios") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    )
                }
            }
        }
    }
}