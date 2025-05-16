package com.example.mytfg

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.mytfg.componentes.TopBar
import com.example.mytfg.componentes.BottomBar
import com.example.mytfg.ui.theme.MyTFGTheme
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {

    private lateinit var authManager: AuthManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseApp.initializeApp(this)

        val googleSignInLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            authManager.handleGoogleSignInResult(
                result.data,
                onSuccess = {
                    Log.d("MainActivity", "Inicio de sesión exitoso con Google.")
                },
                onFailure = { exception ->
                    Log.e("MainActivity", "Error en autenticación de Google", exception)
                }
            )
        }

        authManager = AuthManager(
            activity = this,
            clientId = "448249921559-cff519ita8c2kf8p23msrv5jdcvi7pcd.apps.googleusercontent.com",
            googleSignInLauncher = googleSignInLauncher
        )

        enableEdgeToEdge()
        setContent {
            MyTFGTheme {
                val navHostController = rememberNavController()
                val currentUser by authManager.currentUser.collectAsState()

                androidx.compose.material3.Scaffold(
                    topBar = {
                        TopBar(
                            userName = currentUser?.name ?: "Invitado",
                            avatarUrl = currentUser?.avatarUrl ?: "https://ui-avatars.com/api/?name=Invitado",
                            onProfileClick = { navHostController.navigate("Perfil") }
                        )
                    },
                    bottomBar = {
                        BottomBar(
                            onBack = { navHostController.popBackStack() },
                            onExit = {
                                authManager.logout(
                                    onSuccess = {
                                        navHostController.navigate("PantallaLogin") {
                                            popUpTo(0)
                                        }
                                    },
                                    onFailure = { /* Maneja el error si lo deseas */ }
                                )
                            }
                        )
                    }
                ) { paddingValues ->

                    // 👇 CORREGIDO: pasamos paddingValues a NavigationApp
                    NavigationApp(
                        navHostController = navHostController,
                        authManager = authManager,
                        paddingValues = paddingValues // 👈 Esto es clave
                    )
                }
            }
        }
    }

    fun signInWithGoogle() {
        authManager.signInWithGoogle()
    }
}
