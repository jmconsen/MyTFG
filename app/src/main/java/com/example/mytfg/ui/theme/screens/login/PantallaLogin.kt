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
                    navHostController.navigate("PantallaRegistro")
                },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text("Registrarse con correo electrónico", color = Color.Black)
            }
        }
    }
}
