package com.example.mytfg.ui.theme.screens.perfil

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
import com.example.mytfg.componentes.TopBar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun PantallaAlturaPerfil(navHostController: NavHostController) {
    var altura by remember { mutableFloatStateOf(170f) } // Valor inicial, por ejemplo 170 cm
    var mostrarAlerta by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val user = FirebaseAuth.getInstance().currentUser
    val db = FirebaseFirestore.getInstance()

    // Rango de altura permitido
    val minAltura = 100f
    val maxAltura = 230f

    LaunchedEffect(user) {
        user?.let {
            db.collection("usuarios").document(it.uid).get()
                .addOnSuccessListener { document ->
                    if (document != null && document.contains("altura")) {
                        altura = document.getString("altura")?.toFloatOrNull() ?: 170f
                    }
                }
        }
    }

    Scaffold(
        topBar = {
            TopBar(
                navHostController = navHostController,
                title = "Altura"
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.height(32.dp))
                Text(
                    text = "¿Cuál es tu altura (cm)?",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(128.dp))

                // Slider de altura
                Text(
                    text = "${altura.toInt()} cm",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Slider(
                    value = altura,
                    onValueChange = { altura = it },
                    valueRange = minAltura..maxAltura,
                    steps = ((maxAltura - minAltura).toInt()) - 1, // Un paso por cada cm
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp),
                    colors = SliderDefaults.colors(
                        thumbColor = MaterialTheme.colorScheme.primary,
                        activeTrackColor = MaterialTheme.colorScheme.primary
                    )
                )

                Spacer(Modifier.height(32.dp))
                BotonEstandar(
                    texto = "Continuar",
                    onClick = {
                        user?.let {
                            val datos = mapOf("altura" to altura.toInt().toString())
                            db.collection("usuarios").document(it.uid)
                                .set(datos, SetOptions.merge())
                                .addOnSuccessListener {
                                    mostrarAlerta = true
                                    coroutineScope.launch {
                                        delay(1000)
                                        mostrarAlerta = false
                                        navHostController.navigate("PantallaPesoPerfil")
                                    }
                                }
                        }
                    },
                    enabled = altura in minAltura..maxAltura,
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "6/8",
                    fontSize = 10.sp
                )
            }
        }
    )
}
