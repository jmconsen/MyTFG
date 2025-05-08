package com.example.mytfg

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mytfg.ui.theme.screens.ejercicios.PantallaEjercicios

import com.example.mytfg.ui.theme.screens.login.PantallaLogin
import com.example.mytfg.ui.theme.screens.menu.PantallaMenu
import com.example.mytfg.ui.theme.screens.login.PantallaRegistro

@Composable
fun NavigationApp(
    navHostController: NavHostController,
    authManager: AuthManager,
    modifier: Modifier = Modifier
) {
    val startDestination = if (authManager.isUserLoggedIn()) "PantallaMenu" else "PantallaLogin"

    NavHost(
        navController = navHostController,
        startDestination = startDestination,
    ) {
        // Login / Registro
        composable("PantallaLogin") { PantallaLogin(navHostController) }
        composable("PantallaRegistro") { PantallaRegistro(navHostController) }
        //Menu
        composable("PantallaMenu") { PantallaMenu(navHostController) }
        //My perfil
        //ejercicios
        composable("PantallaEjercicios") { PantallaEjercicios(navHostController) }

    }
}