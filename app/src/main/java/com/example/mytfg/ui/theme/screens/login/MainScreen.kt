package com.example.mytfg.ui.theme.screens.login

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.mytfg.AuthManager
import com.example.mytfg.NavigationApp
import com.example.mytfg.componentes.MenuNavegador
import com.example.mytfg.viewmodel.AuthViewModel

@Composable
fun MainScreen(authManager: AuthManager, navController: NavHostController) {
    val noMenuScreens = listOf("PantallaLogin", "PantallaRegistro", "PantallaWelcome", "PantallaRecuperarPassword")

    // Obtener la ruta actual
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route
    val authViewModel: AuthViewModel = viewModel()

    Scaffold(
        bottomBar = {
            if (currentRoute !in noMenuScreens) {
                MenuNavegador(navController, authManager)
            }
        }
    ) { innerPadding ->
        NavigationApp(
            navHostController = navController,
            authManager = authManager,
            authViewModel = authViewModel,
            modifier = Modifier.padding(innerPadding) // Se pasa el padding correctamente
        )
    }
}