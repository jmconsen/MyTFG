package com.example.mytfg.ui.theme.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.mytfg.MainActivity
import com.example.mytfg.viewmodel.AuthViewModel
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.example.mytfg.R


@Composable
fun PantallaLogin(
    navHostController: NavHostController,
    viewModel: AuthViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    // Navega automáticamente si el login es correcto
    LaunchedEffect(uiState.isLoggedIn) {
        if (uiState.isLoggedIn) {
            navHostController.navigate("PantallaMenu") {
                popUpTo("PantallaLogin") { inclusive = true }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFFF5F5F5), Color(0xFFE0E0E0)) // Cambia por tus colores
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Iniciar Sesión",
                fontSize = 32.sp,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                color = Color(0xFF333333),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            OutlinedTextField(
                value = uiState.email,
                onValueChange = { viewModel.onEmailChange(it) },
                label = { Text("Email") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                textStyle = TextStyle(color = Color.Black),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp)
            )

            OutlinedTextField(
                value = uiState.password,
                onValueChange = { viewModel.onPasswordChange(it) },
                label = { Text("Contraseña") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                textStyle = TextStyle(color = Color.Black),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { viewModel.login() },
                enabled = !uiState.isLoading,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Iniciar Sesión")
            }

            if (uiState.errorMessage != null) {
                Text(
                    text = uiState.errorMessage!!,
                    color = Color.Red,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { (context as? MainActivity)?.signInWithGoogle() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF0181D7)
                )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.iconogoogle),
                        contentDescription = "Google logo",
                        modifier = Modifier.size(24.dp),
                        tint = Color.Unspecified
                    )
<<<<<<< HEAD
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Contraseña") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    textStyle = TextStyle(color = Negro),
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Negro,
                        unfocusedTextColor = Negro,
                        disabledTextColor = Negro,
                        focusedLabelColor = Negro,
                        unfocusedLabelColor = Negro,
                        cursorColor = Negro,
                        focusedBorderColor = Naranja,
                        unfocusedBorderColor = GrisOscuro2
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                BotonEstandar(
                    texto = "Iniciar Sesión",
                    onClick = {
                        if (usuario.isNotBlank() && password.isNotBlank()) {
                            auth.signInWithEmailAndPassword(usuario, password)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        val currentUser = auth.currentUser
                                        currentUser?.let {
                                            mensajeError = "Bienvenido, ${it.email ?: "Usuario"}"
                                        }
                                        navHostController.navigate(route = "PantallaMenu") {
                                            popUpTo("PantallaLogin") { inclusive = true }
                                        }
                                    } else {
                                        mensajeError = task.exception?.message ?: "Error de inicio de sesión"
                                    }
                                }
                        } else {
                            mensajeError = "Por favor, introduzca email y contraseña."
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                LaunchedEffect(mensajeError) {
                    if (mensajeError.startsWith("Bienvenido")) {
                        kotlinx.coroutines.delay(10000)
                        navHostController.navigate(route = "PantallaMenu") {
                            popUpTo("PantallaLogin") { inclusive = true }
                        }
                    }
                }
                if (mensajeError.isNotEmpty()) {
=======
                    Spacer(modifier = Modifier.width(8.dp))
>>>>>>> a7e6f5bcdb4137a3851b44c88108aa7aa42992a8
                    Text(
                        text = "Iniciar Sesión con Google",
                        fontSize = 16.sp,
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

<<<<<<< HEAD
                Button(
                    onClick = { activity?.signInWithGoogle() },
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(
                        start = 4.dp,
                        top = 12.dp,
                        end = 4.dp,
                        bottom = 12.dp
                    ),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF0181D7)
                    )
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.iconogoogle),
                            contentDescription = "Google logo",
                            modifier = Modifier.size(24.dp),
                            tint = Color.Unspecified
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Iniciar Sesión con Google",
                            fontSize = 16.sp,
                            color = Color.White
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                TextButton(
                    onClick = {
                        navHostController.navigate("pantallaRegistro")
                    },
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text("Registrarse con correo electrónico", color = Negro)
                }

                TextButton(
                    onClick = {
                        navHostController.navigate("PantallaRecuperarPassword")
                    },
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text("Recuperar Contraseña", color = Negro)
                }
=======
            TextButton(
                onClick = {
                    navHostController.navigate("PantallaRegistro")
                },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text("Registrarse con correo electrónico", color = Color.Black)
>>>>>>> a7e6f5bcdb4137a3851b44c88108aa7aa42992a8
            }
        }
    }
}
