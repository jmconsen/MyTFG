package com.example.mytfg.ui.theme.screens.ia

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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.mytfg.R
import com.example.mytfg.componentes.BotonEstandar
import com.example.mytfg.componentes.TopBar
import com.example.mytfg.util.generarPlanEntrenamientoIA
import com.example.mytfg.viewmodel.DietaViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

@Composable
fun PantallaFormularioIA(
    navHostController: NavHostController,
    viewModel: DietaViewModel = viewModel()
) {
    val db = FirebaseFirestore.getInstance()
    val userId = FirebaseAuth.getInstance().currentUser?.uid

    val coroutineScope = rememberCoroutineScope()
    var loading by remember { mutableStateOf(false) }
    var resultado by remember { mutableStateOf<String?>(null) }
    var error by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopBar(
                navHostController = navHostController,
                title = "Entrenamiento con IA"
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Imagen de fondo
            Image(
                painter = painterResource(id = R.drawable.imagenia),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // Capa blanca translúcida
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White.copy(alpha = 0.4f))
            )

            // Contenido principal
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                BotonEstandar(
                    texto = "Generar plan de entrenamiento con IA",
                    onClick = {
                        if (userId == null) {
                            error = "Usuario no autenticado"
                            return@BotonEstandar
                        }
                        loading = true
                        resultado = null
                        error = null
                        db.collection("usuarios").document(userId).get()
                            .addOnSuccessListener { document ->
                                if (document != null && document.exists()) {
                                    val edad = document.getString("edad") ?: ""
                                    val peso = document.getString("peso") ?: ""
                                    val altura = document.getString("altura") ?: ""
                                    val frecuencia = document.getString("horasdia") ?: ""
                                    val objetivo = document.getString("objetivo") ?: ""
                                    val lesiones = document.getString("lesiones") ?: ""

                                    when {
                                        edad.isBlank()      -> navHostController.navigate("PantallaEdadPerfil")
                                        altura.isBlank()    -> navHostController.navigate("PantallaAlturaPerfil")
                                        peso.isBlank()      -> navHostController.navigate("PantallaPesoPerfil")
                                        frecuencia.isBlank() -> navHostController.navigate("PantallaTresPerfil")
                                        objetivo.isBlank()  -> navHostController.navigate("PantallaDosPerfil")
                                        lesiones.isBlank()  -> navHostController.navigate("PantallaLesionesPerfil")
                                        else -> {
                                            coroutineScope.launch {
                                                val plan = generarPlanEntrenamientoIA(
                                                    edad, peso, altura, frecuencia, objetivo , lesiones
                                                )
                                                loading = false
                                                if (plan != null) {
                                                    resultado = plan
                                                    db.collection("usuarios").document(userId)
                                                        .update("planEntrenamiento", plan)
                                                        .addOnSuccessListener {
                                                            navHostController.navigate("pantallaPlanGenerado/${plan}")
                                                        }
                                                        .addOnFailureListener { e ->
                                                            error = "Error al guardar: ${e.message}"
                                                        }
                                                } else {
                                                    error = "Error generando el plan"
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    loading = false
                                    error = "No se encontraron datos del usuario."
                                }
                            }
                            .addOnFailureListener { e ->
                                loading = false
                                error = "Error al leer datos: ${e.message}"
                            }
                    },
                    enabled = !loading,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .height(56.dp)
                )

                if (loading) {
                    Spacer(Modifier.height(16.dp))
                    CircularProgressIndicator()
                }
                error?.let {
                    Spacer(Modifier.height(16.dp))
                    Text(it, color = MaterialTheme.colorScheme.error)
                }


                resultado?.let {
                    Spacer(Modifier.height(16.dp))
                    Text("Plan generado:", style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.height(8.dp))
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 100.dp, max = 400.dp)
                            .padding(8.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.9f))
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                                .verticalScroll(rememberScrollState())
                        ) {
                            Text(
                                text = it,
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Black
                            )
                        }
                    }
                }



                /*
                resultado?.let {
                    Spacer(Modifier.height(16.dp))
                    Text("Plan generado:", style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.height(8.dp))
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 100.dp, max = 300.dp)
                            .padding(8.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.9f))
                    ) {
                        Box(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = it,
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Black
                            )
                        }
                    }
                }
                */



                /*
                resultado?.let {
                    Spacer(Modifier.height(16.dp))
                    Text("Plan generado:", style = MaterialTheme.typography.titleMedium)
                    Text(it)
                }

                 */
            }
        }
    }
}
