package com.example.mytfg.ui.theme.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
//import com.example.mytfg.ui.theme.FondoPantallas
import com.example.mytfg.ui.theme.GrisOscuro2
import com.example.mytfg.ui.theme.Naranja

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaMenuOLD(navHostController: NavHostController) {

    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },

        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Menú")
                    }
                },
                /*
                navigationIcon = {
                    IconButton(onClick = { navHostController.popBackStack("PantallaLogin", inclusive = false) }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás")
                    }
                },
                */
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Naranja
                )
            )
        }

    ) { padding ->


        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // Imagen de fondo
            Image(
                painter = painterResource(id = R.drawable.pantalla_menu),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            // Darle opacidad a la imagen
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White.copy(alpha = 0.7f)),// Aplica opacidad
                contentAlignment = Alignment.Center
            )  {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Menú Principal",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = GrisOscuro2,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    BotonEstandar(
                        texto = "Perfil",
                        onClick = { navHostController.navigate("PantallaUnoPerfil") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    BotonEstandar(
                        texto = "Facturas Recibidas",
                        onClick = { navHostController.navigate("PantallaFacturasRecibidas") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}