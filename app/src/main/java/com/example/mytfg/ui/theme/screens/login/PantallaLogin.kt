package com.example.mytfg.ui.theme.screens.login

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.mytfg.MainActivity
import com.example.mytfg.R
import com.example.mytfg.componentes.BotonEstandar
import com.example.mytfg.componentes.BottomBarCopyright
import com.example.mytfg.ui.theme.GrisOscuro2
import com.example.mytfg.ui.theme.Naranja
import com.example.mytfg.ui.theme.Negro
import com.example.mytfg.viewmodel.AuthViewModel

@SuppressLint("ContextCastToActivity")
@Composable
fun PantallaLogin(
    navHostController: NavHostController,
    viewModel: AuthViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val activity = context as? MainActivity

    LaunchedEffect(uiState.isLoggedIn) {
        if (uiState.isLoggedIn) {
            navHostController.navigate("PantallaMenu") {
                popUpTo("PantallaLogin") { inclusive = true }
            }
        }
    }

    Scaffold(
        bottomBar = { BottomBarCopyright() }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0xFFF5F5F5), Color(0xFFE0E0E0))
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
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Negro,
                        unfocusedTextColor = Negro,
                        focusedLabelColor = Negro,
                        unfocusedLabelColor = Negro,
                        cursorColor = Negro,
                        focusedBorderColor = Naranja,
                        unfocusedBorderColor = GrisOscuro2
                    )
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
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Negro,
                        unfocusedTextColor = Negro,
                        focusedLabelColor = Negro,
                        unfocusedLabelColor = Negro,
                        cursorColor = Negro,
                        focusedBorderColor = Naranja,
                        unfocusedBorderColor = GrisOscuro2
                    )
                )

                if (uiState.errorMessage != null) {
                    Text(
                        text = uiState.errorMessage!!,
                        color = Color.Red,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                BotonEstandar(
                    texto = "Iniciar Sesión",
                    onClick = { viewModel.login() },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { activity?.signInWithGoogle() },
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(12.dp),
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
            }
        }
    }
}



/*
package com.example.mytfg.ui.theme.screens.login

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.mytfg.MainActivity
import com.example.mytfg.R
import com.example.mytfg.componentes.BotonEstandar
import com.example.mytfg.ui.theme.GrisOscuro2
import com.example.mytfg.ui.theme.Naranja
import com.example.mytfg.ui.theme.Negro
import com.example.mytfg.viewmodel.AuthViewModel

@SuppressLint("ContextCastToActivity")
@Composable
fun PantallaLogin(
    navHostController: NavHostController,
    viewModel: AuthViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val activity = context as? MainActivity

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
                    colors = listOf(Color(0xFFF5F5F5), Color(0xFFE0E0E0))
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
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Negro,
                    unfocusedTextColor = Negro,
                    focusedLabelColor = Negro,
                    unfocusedLabelColor = Negro,
                    cursorColor = Negro,
                    focusedBorderColor = Naranja,
                    unfocusedBorderColor = GrisOscuro2
                )
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
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Negro,
                    unfocusedTextColor = Negro,
                    focusedLabelColor = Negro,
                    unfocusedLabelColor = Negro,
                    cursorColor = Negro,
                    focusedBorderColor = Naranja,
                    unfocusedBorderColor = GrisOscuro2
                )
            )

            if (uiState.errorMessage != null) {
                Text(
                    text = uiState.errorMessage!!,
                    color = Color.Red,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            BotonEstandar(
                texto = "Iniciar Sesión",
                onClick = { viewModel.login() },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { activity?.signInWithGoogle() },
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(12.dp),
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
        }
    }
}
*/

/*
import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.mytfg.componentes.BotonEstandar
import com.example.mytfg.ui.theme.GrisOscuro2
import com.example.mytfg.ui.theme.Naranja
import com.example.mytfg.ui.theme.Negro
import com.google.firebase.auth.FirebaseAuth


@SuppressLint("ContextCastToActivity")
@Composable
fun PantallaLogin(
    navHostController: NavHostController,
    viewModel: AuthViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    var usuario by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var mensajeError by remember { mutableStateOf("") }
    val auth = FirebaseAuth.getInstance()
    val activity = LocalContext.current as? MainActivity

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

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = "Iniciar Sesión con Google",
                        fontSize = 16.sp,
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

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

            TextButton(
                onClick = {
                    navHostController.navigate("PantallaRegistro")
                },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text("Registrarse con correo electrónico", color = Color.Black)
            }
        }
    }
    }
}
*/


