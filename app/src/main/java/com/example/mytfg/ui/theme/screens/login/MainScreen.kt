package com.example.mytfg.ui.theme.screens.login

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.mytfg.AuthManager
import com.example.mytfg.NavigationApp
import com.example.mytfg.componentes.MenuNavegador

@Composable
fun MainScreen(authManager: AuthManager, navController: NavHostController) {
    val noMenuScreens = listOf("PantallaLogin", "PantallaRegistro")

    // Obtener la ruta actual
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

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
            modifier = Modifier.padding(innerPadding) // Se pasa el padding correctamente
        )
    }
}