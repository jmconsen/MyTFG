package com.example.mytfg.ui.theme.screens.perfil

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mytfg.componentes.BotonEstandar
import com.example.mytfg.ui.theme.Naranja
import com.example.mytfg.ui.theme.NaranjaClaro
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import com.example.mytfg.componentes.TopBar
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.rounded.*
import androidx.compose.material.icons.sharp.*
import androidx.compose.material.icons.twotone.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.mytfg.R
import com.example.mytfg.ui.theme.NaranjaClaro
//import androidx.compose.ui.text.font.FontWeight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaUnoPerfil(navHostController: NavHostController) {
    var selectedOption by remember { mutableStateOf<String?>(null) }

    val options = listOf(
        "Quiero bajar de peso" to Icons.Filled.FitnessCenter,
        "Quiero probar AI Coach" to Icons.Filled.Smartphone,
        "Quiero ganar volumen" to Icons.Filled.FitnessCenter,
        "Quiero ganar resistencia" to Icons.Filled.DirectionsRun,
        "¡Solo estoy probando la app!" to Icons.Filled.ThumbUp
    )

    val user = FirebaseAuth.getInstance().currentUser
    var userName by remember { mutableStateOf<String?>(null) }
    val db = FirebaseFirestore.getInstance()


    // Leer el objetivo y usuario existente si existe
    LaunchedEffect(user) {
        user?.let {
            db.collection("usuarios").document(it.uid).get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        if (document.contains("objetivo")) {
                            selectedOption = document.getString("objetivo")
                        }
                        if (document.contains("nombre")) {
                            userName = document.getString("nombre")
                        }
                    }
                }
                .addOnFailureListener { e ->
                    Log.e("PantallaUnoPerfil", "Error al leer: ${e.message}")
                }
        }
    }

    Scaffold(
        topBar = {
            TopBar(
                navHostController = navHostController,
                title = "Objetivo"
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
                    painter = painterResource(id = R.drawable.objetivos),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // Capa blanca translúcida
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White.copy(alpha = 0.7f))
                )

                // Contenido principal con scroll
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState()) // Habilitar scroll
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "¿Cuál es tu objetivo/meta de ejercicios?",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    Text(
                        text = "Selecciona una opción:",
                        fontSize = 18.sp,
                        modifier = Modifier.padding(bottom = 32.dp)
                    )

                    options.forEach { (option, icon) ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                                .clickable { selectedOption = option },
                            colors = CardDefaults.cardColors(containerColor = NaranjaClaro),
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = icon,
                                        contentDescription = null,
                                        modifier = Modifier.padding(end = 8.dp)
                                    )
                                    Text(
                                        text = option,
                                        fontSize = 16.sp
                                    )
                                }
                                RadioButton(
                                    selected = selectedOption == option,
                                    onClick = { selectedOption = option },
                                    colors = RadioButtonDefaults.colors(
                                        selectedColor = Naranja
                                    )
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 72.dp)
                            .padding(horizontal = 32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        BotonEstandar(
                            texto = "Continuar",
                            onClick = {
                                user?.let {
                                    val datos = mapOf("objetivo" to selectedOption)

                                    db.collection("usuarios").document(it.uid)
                                        .set(datos, SetOptions.merge())
                                        .addOnSuccessListener {
                                            navHostController.navigate("PantallaDosPerfil")
                                        }
                                        .addOnFailureListener { e ->
                                            Log.e("PantallaUnoPerfil", "Error al guardar: ${e.message}")
                                        }
                                }
                            },
                            enabled = selectedOption != null,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "2/8",
                            fontSize = 10.sp
                        )
                    }
                }
            }
        }
    )
}
