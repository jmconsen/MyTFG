package com.example.mytfg.ui.theme.screens.perfil

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.example.mytfg.componentes.TopBar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun PantallaEdadPerfil(navHostController: NavHostController) {
    var edad by remember { mutableStateOf(25f) } // Valor inicial, por ejemplo 25 años
    var mostrarAlerta by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val user = FirebaseAuth.getInstance().currentUser
    val db = FirebaseFirestore.getInstance()

    // Rango de edad permitido
    val minEdad = 10f
    val maxEdad = 100f

    LaunchedEffect(user) {
        user?.let {
            db.collection("usuarios").document(it.uid).get()
                .addOnSuccessListener { document ->
                    if (document != null && document.contains("edad")) {
                        edad = document.getString("edad")?.toFloatOrNull() ?: 25f
                    }
                }
        }
    }

    Scaffold(
        topBar = {
            TopBar(
                navHostController = navHostController,
                title = "Edad"
            )
        },

        content = { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                // Imagen de fondo
                Image(
                    painter = painterResource(id = R.drawable.editaredadreloj),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // Capa blanca translúcida
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White.copy(alpha = 0.5f))
                )

                // Contenido principal con scroll
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Spacer(Modifier.height(16.dp))

                    Text(
                        text = "¿Cuál es tu edad?",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )

                    Spacer(Modifier.height(128.dp))

                    // Slider de edad
                    Text(
                        text = "${edad.toInt()} años",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Slider(
                        value = edad,
                        onValueChange = { edad = it },
                        valueRange = minEdad..maxEdad,
                        steps = ((maxEdad - minEdad).toInt()) - 1,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 32.dp),
                        colors = SliderDefaults.colors(
                            thumbColor = MaterialTheme.colorScheme.primary,
                            activeTrackColor = MaterialTheme.colorScheme.primary
                        )
                    )

                    Spacer(Modifier.height(72.dp))

                    BotonEstandar(
                        texto = "Continuar",
                        onClick = {
                            user?.let {
                                val datos = mapOf("edad" to edad.toInt().toString())
                                db.collection("usuarios").document(it.uid)
                                    .set(datos, SetOptions.merge())
                                    .addOnSuccessListener {
                                        mostrarAlerta = true
                                        coroutineScope.launch {
                                            delay(1000)
                                            mostrarAlerta = false
                                            navHostController.navigate("PantallaAlturaPerfil")
                                        }
                                    }
                            }
                        },
                        enabled = edad in minEdad..maxEdad,
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "5/8",
                        fontSize = 10.sp
                    )
                }
            }
        }
    )
}
