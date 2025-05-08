package com.example.mytfg

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mytfg.ui.theme.MyTFGTheme
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {
    private lateinit var navHostController: NavHostController
    private lateinit var authManager: AuthManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializa Firebase (si no lo haces en Application)
        FirebaseApp.initializeApp(this)

        // Configuración del inicio de sesión con Google
        val googleSignInLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            authManager.handleGoogleSignInResult(
                result.data,
                onSuccess = {
                    Log.d("MainActivity", "Inicio de sesión exitoso con Google.")
                    if (::navHostController.isInitialized) {
                        navHostController.navigate("PantallaMenu") {
                            popUpTo("PantallaLogin") { inclusive = true }
                        }
                    } else {
                        Log.e("MainActivity", "navHostController no inicializado")
                    }
                },
                onFailure = { exception ->
                    Log.e("MainActivity", "Error en autenticación de Google", exception)
                }
            )
        }

        // Inicializar AuthManager
        authManager = AuthManager(
            activity = this,
            clientId = "448249921559-cff519ita8c2kf8p23msrv5jdcvi7pcd.apps.googleusercontent.com",
            googleSignInLauncher = googleSignInLauncher
        )

        // Configurar UI
        enableEdgeToEdge()
        setContent {
            MyTFGTheme {
                navHostController = rememberNavController()
                // Usa tu NavigationApp para centralizar la navegación
                NavigationApp(
                    navHostController = navHostController,
                    authManager = authManager
                )
            }
        }
    }

    fun signInWithGoogle() {
        authManager.signInWithGoogle()
    }
}
