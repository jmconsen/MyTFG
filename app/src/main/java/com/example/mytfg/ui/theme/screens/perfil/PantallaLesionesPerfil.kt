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
fun PantallaLesionesPerfil(navHostController: NavHostController) {
    var lesiones by remember { mutableStateOf("") }
    var mostrarAlerta by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val user = FirebaseAuth.getInstance().currentUser
    val db = FirebaseFirestore.getInstance()

    LaunchedEffect(user) {
        user?.let {
            db.collection("usuarios").document(it.uid).get()
                .addOnSuccessListener { document ->
                    if (document != null && document.contains("lesiones")) {
                        lesiones = document.getString("lesiones") ?: ""
                    }
                }
        }
    }

    Scaffold(
        topBar = {
            TopBar(
                navHostController = navHostController,
                title = "Lesiones"
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
                    text = "¿Tienes alguna lesión o limitación física?",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(128.dp))

                OutlinedTextField(
                    value = lesiones,
                    onValueChange = { lesiones = it },
                    label = { Text("Describe tus lesiones (si tienes)") },
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp)
                )
                Spacer(Modifier.height(32.dp))
                BotonEstandar(
                    texto = "Finalizar",
                    onClick = {
                        user?.let {
                            val datos = mapOf("lesiones" to lesiones)
                            db.collection("usuarios").document(it.uid)
                                .set(datos, SetOptions.merge())
                                .addOnSuccessListener {
                                    mostrarAlerta = true
                                    coroutineScope.launch {
                                        delay(1000)
                                        mostrarAlerta = false
                                        navHostController.navigate("PantallaMenu")
                                    }
                                }
                        }
                    },
                    enabled = lesiones.isNotBlank(),
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "8/8",
                    fontSize = 10.sp
                )
            }
        }
    )

    if (mostrarAlerta) {
        AlertDialog(
            onDismissRequest = { mostrarAlerta = false },
            confirmButton = {
                TextButton(onClick = { mostrarAlerta = false }) { Text("OK") }
            },
            text = { Text("Respuestas guardadas correctamente") }
        )
    }
}
