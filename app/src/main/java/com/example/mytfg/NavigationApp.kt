package com.example.mytfg

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.mytfg.ui.theme.screens.ejercicios.PantallaCategorias
import com.example.mytfg.ui.theme.screens.ejercicios.PantallaEjercicios
import com.example.mytfg.ui.theme.screens.ejercicios.PantallaDetalleEjercicio
import com.example.mytfg.ui.theme.screens.login.PantallaLogin
import com.example.mytfg.ui.theme.screens.menu.PantallaMenu
import com.example.mytfg.ui.theme.screens.login.PantallaRegistro
import com.example.mytfg.ui.theme.screens.dieta.PantallaDieta
import com.example.mytfg.ui.theme.screens.dieta.PantallaSeleccionDieta


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
        modifier = modifier
    ) {
        // Login / Registro
        composable("PantallaLogin") { PantallaLogin(navHostController) }
        composable("PantallaRegistro") { PantallaRegistro(navHostController) }

        // Menú principal
        composable("PantallaMenu") { PantallaMenu(navHostController) }

        // Categorías de ejercicios
        composable("PantallaCategorias") { PantallaCategorias(navHostController) }

        // Lista de ejercicios según parte del cuerpo y nivel
        composable(
            route = "PantallaEjercicios/{bodyPart}/{nivel}",
            arguments = listOf(
                navArgument("bodyPart") { type = NavType.StringType },
                navArgument("nivel") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val bodyPart = backStackEntry.arguments?.getString("bodyPart") ?: ""
            val nivel = backStackEntry.arguments?.getString("nivel") ?: ""
            PantallaEjercicios(navHostController, bodyPart, nivel)
        }

        // Detalle de un ejercicio concreto
        composable(
            route = "PantallaDetalleEjercicio/{ejercicioId}",
            arguments = listOf(
                navArgument("ejercicioId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val ejercicioId = backStackEntry.arguments?.getString("ejercicioId") ?: ""
            PantallaDetalleEjercicio(navHostController, ejercicioId)
        }
        // Pantallas de dieta
        composable("PantallaSeleccionDieta") {
            PantallaSeleccionDieta(navHostController)
        }
        composable(
            "PantallaDieta/{objetivo}",
            arguments = listOf(navArgument("objetivo") { type = NavType.StringType })
        ) { backStackEntry ->
            val objetivo = backStackEntry.arguments?.getString("objetivo") ?: ""
            PantallaDieta(navHostController, objetivo)
        }

    }
}
