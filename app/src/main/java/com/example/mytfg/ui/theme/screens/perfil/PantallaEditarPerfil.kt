package com.example.mytfg.ui.theme.screens.perfil

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mytfg.R
import com.example.mytfg.componentes.BotonEstandar
import com.example.mytfg.componentes.BottomBarCopyright
import com.example.mytfg.componentes.TopBar
import com.example.mytfg.ui.theme.GrisOscuro2
import com.example.mytfg.ui.theme.Naranja
import com.example.mytfg.ui.theme.Negro
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun PantallaEditarPerfil(
    navController: NavHostController
) {
    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var mensajeError by remember { mutableStateOf("") }
    var mensajeExito by remember { mutableStateOf("") }

    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()
    val user = auth.currentUser

    LaunchedEffect(user) {
        user?.let {
            db.collection("usuarios").document(it.uid).get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        nombre = document.getString("nombre") ?: ""
                        email = document.getString("email") ?: ""
                    }
                }
                .addOnFailureListener { e ->
                    mensajeError = "Error al cargar datos: ${e.message}"
                }
        }
    }

    Scaffold(
        topBar = {
            TopBar(
                navHostController = navController,
                title = "Editar Perfil"
            )
        },
        bottomBar = { BottomBarCopyright() }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            // Imagen de fondo
            Image(
                painter = painterResource(id = R.drawable.editarperfil1),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // Capa blanca translúcida
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White.copy(alpha = 0.6f))
            )


            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = "Actualiza los datos de tu perfil",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Spacer(modifier = Modifier.height(32.dp))

                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    textStyle = TextStyle(color = Negro),
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Negro,
                        unfocusedTextColor = Negro,
                        cursorColor = Negro,
                        focusedBorderColor = Naranja,
                        unfocusedBorderColor = Negro
                    ),
                    singleLine = true
                )

                OutlinedTextField(
                    value = email,
                    onValueChange = { },
                    label = { Text("Correo Electrónico") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    textStyle = TextStyle(color = Color.Gray),
                    shape = RoundedCornerShape(16.dp),
                    enabled = false,
                    colors = OutlinedTextFieldDefaults.colors(
                        disabledTextColor = Color.Gray,
                        disabledBorderColor = Color.LightGray
                    ),
                    singleLine = true
                )

                Text(
                    text = "El correo electrónico no se puede modificar.",
                    color = Negro,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )

                TextButton(
                    onClick = {
                        navController.navigate("PantallaRecuperarPassword?email=${email}")
                    }
                ) {
                    Text(
                        text = "Recuperar contraseña",
                        color = Naranja,
                        fontSize = 14.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                BotonEstandar(
                    texto = "Continuar",
                    onClick = {
                        user?.let {
                            actualizarDatos(user.uid, nombre, email, db, navController) {
                                mensajeExito = it
                                mensajeError = ""
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "1/8",
                    fontSize = 10.sp
                )

                if (mensajeError.isNotEmpty()) {
                    Text(
                        text = mensajeError,
                        color = Color.Red,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                if (mensajeExito.isNotEmpty()) {
                    Text(
                        text = mensajeExito,
                        color = Color.Green,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }
    }
}

private fun actualizarDatos(
    uid: String,
    nombre: String,
    email: String,
    db: FirebaseFirestore,
    navController: NavHostController,
    onSuccess: (String) -> Unit
) {
    val datosActualizados = mapOf(
        "nombre" to nombre,
        "email" to email
    )

    db.collection("usuarios").document(uid)
        .update(datosActualizados)
        .addOnSuccessListener {
            onSuccess("Datos actualizados correctamente.")
            navController.navigate("PantallaUnoPerfil")
        }
        .addOnFailureListener { e ->
            onSuccess("Error al actualizar datos: ${e.message}")
        }
}
