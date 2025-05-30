package com.example.mytfg.ui.theme.screens.perfil

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HdrOff
import androidx.compose.material.icons.filled.HdrStrong
import androidx.compose.material.icons.filled.HdrWeak
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mytfg.componentes.BotonEstandar
import com.example.mytfg.componentes.TopBar
import com.example.mytfg.ui.theme.Naranja
import androidx.compose.ui.graphics.vector.ImageVector
//import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.rounded.*
import androidx.compose.material.icons.sharp.*
import androidx.compose.material.icons.twotone.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.text.font.FontWeight
import com.example.mytfg.componentes.TopBar
import com.example.mytfg.ui.theme.NaranjaClaro
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.ui.res.painterResource
import com.example.mytfg.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaTresPerfil(navHostController: NavHostController) {
    var selectedOption by remember { mutableStateOf<String?>(null) }
    var mostrarAlerta by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    val options = listOf(
        "Hasta 1 hora" to Icons.Filled.HdrStrong,
        "Entre 1 y 2 horas" to Icons.Filled.HdrWeak,
        "Más de 3 horas" to Icons.Filled.HdrOff
    )

    val user = FirebaseAuth.getInstance().currentUser
    val db = FirebaseFirestore.getInstance()

    // Leer intensidad guardada si existe
    LaunchedEffect(user) {
        user?.let {
            db.collection("usuarios").document(it.uid).get()
                .addOnSuccessListener { document ->
                    if (document != null && document.contains("horasdia")) {
                        selectedOption = document.getString("horasdia")
                    }
                }
                .addOnFailureListener { e ->
                    Log.e("PantallaTresPerfil", "Error al leer: ${e.message}")
                }
        }
    }

    Scaffold(

        topBar = {
            TopBar(
                navHostController = navHostController,
                title = "Horas Diarias"
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
                    painter = painterResource(id = R.drawable.horas_diarias),
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

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "¿Cuántas horas deseas dedicar al día?",
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
                                    val datos = mapOf("horasdia" to selectedOption)
                                    db.collection("usuarios").document(it.uid)
                                        .set(datos, SetOptions.merge())
                                        .addOnSuccessListener {
                                            mostrarAlerta = true
                                            coroutineScope.launch {
                                                delay(1000)
                                                mostrarAlerta = false
                                                navHostController.navigate("PantallaEdadPerfil")
                                            }
                                        }
                                        .addOnFailureListener { e ->
                                            Log.e(
                                                "PantallaTresPerfil",
                                                "Error al guardar: ${e.message}"
                                            )
                                        }
                                }
                            },
                            enabled = selectedOption != null,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "4/8",
                            fontSize = 10.sp
                        )
                    }
                }
            }
        }
    )
}
