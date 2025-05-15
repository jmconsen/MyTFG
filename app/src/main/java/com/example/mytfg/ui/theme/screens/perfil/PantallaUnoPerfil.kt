package com.example.mytfg.ui.theme.screens.perfil

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
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
    val db = FirebaseFirestore.getInstance()

    // Leer el objetivo existente si existe
    LaunchedEffect(user) {
        user?.let {
            db.collection("usuarios").document(it.uid).get()
                .addOnSuccessListener { document ->
                    if (document != null && document.contains("objetivo")) {
                        selectedOption = document.getString("objetivo")
                    }
                }
                .addOnFailureListener { e ->
                    Log.e("PantallaUnoPerfil", "Error al leer: \${e.message}")
                }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Perfil")
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navHostController.popBackStack("PantallaMenu", inclusive = false) }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás")
                    }
                },
                actions = {
                    IconButton(onClick = { navHostController.navigate("PantallaMenu") }) {
                        Icon(Icons.Filled.Menu, contentDescription = "Menú")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Naranja
                )
            )
        },
        content = { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "¿Cuál es tu objetivo/meta de ejercicios?",
                        fontSize = 24.sp,
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
                                            Log.e("PantallaUnoPerfil", "Error al guardar: \${e.message}")
                                        }
                                }
                            },
                            enabled = selectedOption != null,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "1/3",
                            fontSize = 10.sp
                        )
                    }
                }
            }
        }
    )
}

/*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.layout.FlowColumnScopeInstance.weight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mytfg.componentes.BotonEstandar
import com.example.mytfg.ui.theme.Naranja
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.rounded.*
import androidx.compose.material.icons.sharp.*
import androidx.compose.material.icons.twotone.*
import com.example.mytfg.ui.theme.NaranjaClaro
import androidx.compose.ui.text.font.FontWeight
*/
/*
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

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Perfil")
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navHostController.popBackStack("PantallaMenu", inclusive = false) }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás")
                    }
                },
                actions = {
                    IconButton(onClick = { navHostController.navigate("PantallaMenu") }) {
                        Icon(Icons.Filled.Menu, contentDescription = "Menú")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Naranja
                )
            )
        },
        content = { padding ->

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                        //.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Text(
                        text = "¿Cuál es tu objetivo/meta de ejercicios?",
                        fontSize = 24.sp,
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
                            onClick = { navHostController.navigate("PantallaDosPerfil") },
                            enabled = selectedOption != null,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "1/3",
                            fontSize = 10.sp
                        )
                    }
                    //Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    )
}
*/