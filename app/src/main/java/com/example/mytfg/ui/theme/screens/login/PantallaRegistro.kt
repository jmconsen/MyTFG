package com.example.mytfg.ui.theme.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mytfg.componentes.BotonEstandar
import com.example.mytfg.ui.theme.AzulClaro
import com.example.mytfg.ui.theme.FondoPantallas
import com.example.mytfg.ui.theme.GrisOscuro2
import com.example.mytfg.ui.theme.Naranja
import com.example.mytfg.ui.theme.Negro
import com.google.firebase.auth.FirebaseAuth
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextButton
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import com.example.mytfg.R
import com.example.mytfg.componentes.BottomBarCopyright
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun PantallaRegistro(
    navController: NavController
) {
    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var mensajeError by remember { mutableStateOf("") }

    val auth = FirebaseAuth.getInstance()

    Scaffold(
        bottomBar = { BottomBarCopyright() }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            // Imagen de fondo
            Image(
                painter = painterResource(id = R.drawable.image_registro),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            // Capa translúcida sobre la imagen
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White.copy(alpha = 0.7f))
            )

            // Contenido principal encima de la capa translúcida
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Registro de Usuario",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Negro,
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
                        //disabledTextColor = Negro,
                        focusedLabelColor = Negro,
                        unfocusedLabelColor = Negro,
                        cursorColor = Negro,
                        focusedBorderColor = Naranja,
                        unfocusedBorderColor = GrisOscuro2
                    ),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
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
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Negro,
                        unfocusedTextColor = Negro,
                        //disabledTextColor = Negro,
                        focusedLabelColor = Negro,
                        unfocusedLabelColor = Negro,
                        cursorColor = Negro,
                        focusedBorderColor = Naranja,
                        unfocusedBorderColor = GrisOscuro2
                    ),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Contraseña") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    textStyle = TextStyle(color = Negro),
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Negro,
                        unfocusedTextColor = Negro,
                        //disabledTextColor = Negro,
                        focusedLabelColor = Negro,
                        unfocusedLabelColor = Negro,
                        cursorColor = Negro,
                        focusedBorderColor = Naranja,
                        unfocusedBorderColor = GrisOscuro2
                    ),
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation()
                )

                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = { Text("Confirmar Contraseña") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    textStyle = TextStyle(color = Negro),
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Negro,
                        unfocusedTextColor = Negro,
                        //disabledTextColor = Negro,
                        focusedLabelColor = Negro,
                        unfocusedLabelColor = Negro,
                        cursorColor = Negro,
                        focusedBorderColor = Naranja,
                        unfocusedBorderColor = GrisOscuro2
                    ),
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation()
                )

                Spacer(modifier = Modifier.height(16.dp))

                BotonEstandar(
                    texto = "Registrarse",
                    onClick = {
                        if (nombre.isNotBlank() && email.isNotBlank() && password.isNotBlank() && confirmPassword.isNotBlank()) {
                            if (password == confirmPassword) {
                                registrarUsuario(nombre, email, password, auth) { resultado ->
                                    if (resultado == "Éxito") {
                                        navController.navigate("pantallaLogin")
                                    } else {
                                        mensajeError = resultado
                                    }
                                }
                            } else {
                                mensajeError = "Las contraseñas no coinciden."
                            }
                        } else {
                            mensajeError = "Por favor, complete todos los campos."
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                TextButton(
                    onClick = {
                        navController.navigate("PantallaLogin")
                    },
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text("Ya estoy Registrado. Ir a Login", color = Negro)
                }

                /*
                Spacer(modifier = Modifier.height(16.dp))

                BotonEstandar(
                    texto = "Pantalla Login",
                    onClick = { navController.navigate(route = "PantallaLogin") },
                    modifier = Modifier.fillMaxWidth()
                )
                */

                if (mensajeError.isNotEmpty()) {
                    Text(
                        text = mensajeError,
                        color = Color.Red,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }
    }
}

fun registrarUsuario(nombre: String, email: String, password: String, auth: FirebaseAuth, onResult: (String) -> Unit) {
    val db = FirebaseFirestore.getInstance()

    auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser
                val uid = user?.uid

                if (uid != null) {
                    val datosUsuario = mapOf(
                        "nombre" to nombre,
                        "email" to email,
                        "fechaRegistro" to Timestamp.now()
                    )

                    db.collection("usuarios").document(uid)
                        .set(datosUsuario)
                        .addOnSuccessListener {
                            onResult("Éxito")
                        }
                        .addOnFailureListener { e ->
                            onResult("Usuario creado, pero error al guardar en base de datos: ${e.message}")
                        }
                } else {
                    onResult("Usuario creado, pero UID no disponible.")
                }
            } else {
                if (task.exception is FirebaseAuthUserCollisionException) {
                    onResult("El correo ya está registrado.")
                } else {
                    onResult(task.exception?.message ?: "Error al registrar")
                }
            }
        }
}

/*
fun registrarUsuario(
    nombre: String,
    email: String,
    password: String,
    auth: FirebaseAuth,
    onResult: (String) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onResult("Éxito")
            } else {
                onResult(task.exception?.message ?: "Error al registrar")
            }
        }
}

 */