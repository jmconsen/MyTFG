package com.example.mytfg.ui.theme.screens.perfil

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
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
    var esGoogle by remember { mutableStateOf(false) }

    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()
    val user = auth.currentUser

    // Cargar datos del usuario
    LaunchedEffect(user) {
        user?.let {
            // Detectar si el usuario inició sesión con Google
            esGoogle = it.providerData.any { profile -> profile.providerId == "google.com" }

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
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Actualiza los datos de tu perfil",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

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
                        unfocusedBorderColor = GrisOscuro2
                    ),
                    singleLine = true
                )

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Correo Electrónico") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    textStyle = TextStyle(color = Negro),
                    shape = RoundedCornerShape(16.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    enabled = !esGoogle,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Negro,
                        unfocusedTextColor = Negro,
                        cursorColor = Negro,
                        focusedBorderColor = Naranja,
                        unfocusedBorderColor = GrisOscuro2,
                        disabledTextColor = Color.Gray,
                        disabledBorderColor = Color.LightGray
                    ),
                    singleLine = true
                )

                if (esGoogle) {
                    Text(
                        text = "No puedes cambiar tu correo si iniciaste sesión con Google.",
                        color = Color.Gray,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                TextButton(
                    onClick = {
                        navController.navigate("PantallaRecuperarPassword?email=${email}")
                    },
                    enabled = !esGoogle
                ) {
                    Text(
                        text = "Recuperar contraseña",
                        color = if (esGoogle) Color.Gray else Naranja,
                        fontSize = 14.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                BotonEstandar(
                    texto = "Continuar",
                    onClick = {
                        user?.let {
                            if (!esGoogle && email != user.email) {
                                user.updateEmail(email)
                                    .addOnSuccessListener {
                                        actualizarDatos(user.uid, nombre, email, db, navController) {
                                            mensajeExito = it
                                            mensajeError = ""
                                        }
                                    }
                                    .addOnFailureListener { e ->
                                        mensajeError = when (e) {
                                            is FirebaseAuthUserCollisionException -> "Este correo ya está registrado."
                                            is FirebaseAuthRecentLoginRequiredException -> "Por seguridad, vuelve a iniciar sesión para cambiar tu correo."
                                            else -> "No se pudo actualizar el correo: ${e.message}"
                                        }
                                        mensajeExito = ""
                                    }
                            } else {
                                actualizarDatos(user.uid, nombre, email, db, navController) {
                                    mensajeExito = it
                                    mensajeError = ""
                                }
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
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


/*
package com.example.mytfg.ui.theme.screens.perfil

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mytfg.componentes.BotonEstandar
import com.example.mytfg.componentes.BottomBarCopyright
import com.example.mytfg.componentes.TopBar
import com.example.mytfg.ui.theme.GrisOscuro2
import com.example.mytfg.ui.theme.Naranja
import com.example.mytfg.ui.theme.Negro
import com.google.firebase.auth.FirebaseAuth
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

    // Cargar datos del usuario
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
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Actualizar los datos de tu perfil",
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

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
                        unfocusedBorderColor = GrisOscuro2
                    ),
                    singleLine = true
                )

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Correo Electrónico") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    textStyle = TextStyle(color = Negro),
                    shape = RoundedCornerShape(16.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Negro,
                        unfocusedTextColor = Negro,
                        cursorColor = Negro,
                        focusedBorderColor = Naranja,
                        unfocusedBorderColor = GrisOscuro2
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

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
                            if (email != user.email) {
                                user.updateEmail(email)
                                    .addOnSuccessListener {
                                        actualizarDatos(user.uid, nombre, email, db, navController) {
                                            mensajeExito = it
                                            mensajeError = ""
                                        }
                                    }
                                    .addOnFailureListener { e ->
                                        mensajeError = when (e) {
                                            is FirebaseAuthUserCollisionException -> "No es posible actualizar el email, debido a que ya aparece registrado."
                                            else -> "No es posible actualizar el correo. Es posible que ya exista"
                                            //else -> "Error al actualizar el correo: ${e.message}"
                                        }
                                        mensajeExito = ""
                                    }
                            } else {
                                actualizarDatos(user.uid, nombre, email, db, navController) {
                                    mensajeExito = it
                                    mensajeError = ""
                                }
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
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
 */
