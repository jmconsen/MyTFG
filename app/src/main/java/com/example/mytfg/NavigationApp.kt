package com.example.mytfg

import PantallaWelcome
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
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
import com.example.mytfg.ui.theme.screens.login.PantallaRecuperarPassword
import com.example.mytfg.ui.theme.screens.perfil.PantallaUnoPerfil
import com.example.mytfg.ui.theme.screens.perfil.PantallaDosPerfil
import com.example.mytfg.ui.theme.screens.perfil.PantallaTresPerfil
import com.example.mytfg.ui.theme.screens.dieta.PantallaDieta
import com.example.mytfg.ui.theme.screens.dieta.PantallaSeleccionDieta
import com.example.mytfg.ui.theme.screens.ia.PantallaFormularioIA
import com.example.mytfg.ui.theme.screens.ia.PantallaPlanGenerado
import com.example.mytfg.ui.theme.screens.perfil.PantallaEditarPerfil
import com.example.mytfg.ui.theme.screens.perfil.PantallaEdadPerfil
import com.example.mytfg.ui.theme.screens.perfil.PantallaAlturaPerfil
import com.example.mytfg.ui.theme.screens.perfil.PantallaPesoPerfil
import com.example.mytfg.ui.theme.screens.perfil.PantallaLesionesPerfil
import com.example.mytfg.viewmodel.AuthViewModel


@Composable
fun NavigationApp(
    navHostController: NavHostController,
    authManager: AuthManager,
    authViewModel: AuthViewModel,
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(0.dp)
) {
    val startDestination = if (authManager.isUserLoggedIn()) "PantallaMenu" else "PantallaWelcome"

    NavHost(
        navController = navHostController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable("PantallaWelcome") { PantallaWelcome(navHostController) }

        composable("PantallaMenu") {
            val uiState by authViewModel.uiState.collectAsState()
            PantallaMenu(
                navHostController = navHostController,
                userName = uiState.usuarioAuth.name,
                avatarUrl = uiState.usuarioAuth.avatarUrl
            )
        }

        composable("PantallaLogin") { PantallaLogin(navHostController) }
        composable("PantallaRegistro") { PantallaRegistro(navHostController) }
        composable("PantallaUnoPerfil") { PantallaUnoPerfil(navHostController) }
        composable("PantallaDosPerfil") { PantallaDosPerfil(navHostController) }
        composable("PantallaTresPerfil") { PantallaTresPerfil(navHostController) }
        composable("PantallaEdadPerfil") { PantallaEdadPerfil(navHostController) }
        composable("PantallaAlturaPerfil") { PantallaAlturaPerfil(navHostController) }
        composable("PantallaPesoPerfil") { PantallaPesoPerfil(navHostController) }
        composable("PantallaLesionesPerfil") { PantallaLesionesPerfil(navHostController) }
        composable("PantallaEditarPerfil") { PantallaEditarPerfil(navHostController) }
        composable("PantallaFormularioIA") { PantallaFormularioIA(navHostController) }
        composable("PantallaUnoPerfil") { PantallaUnoPerfil(navHostController) }
        composable("PantallaDosPerfil") { PantallaDosPerfil(navHostController) }
        composable("PantallaTresPerfil") { PantallaTresPerfil(navHostController) }
        composable("PantallaEditarPerfil") { PantallaEditarPerfil(navHostController) }
        composable("pantallaPlanGenerado/{plan}") { backStackEntry ->
            val plan = backStackEntry.arguments?.getString("plan") ?: ""
            PantallaPlanGenerado(
                navHostController = navHostController,
                plan = plan
            )
        }

        composable(
            route = "PantallaRecuperarPassword?email={email}",
            arguments = listOf(navArgument("email") { defaultValue = "" })
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            PantallaRecuperarPassword(email = email, navHostController = navHostController)
        }

        composable("PantallaCategorias") {
            PantallaCategorias(
                navHostController = navHostController,
                paddingValues = paddingValues
            )
        }

        composable(
            route = "PantallaEjercicios/{bodyPart}/{equipo}",
            arguments = listOf(
                navArgument("bodyPart") { type = NavType.StringType },
                navArgument("equipo") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val bodyPart = backStackEntry.arguments?.getString("bodyPart") ?: ""
            val equipo = backStackEntry.arguments?.getString("equipo") ?: "body weight"
            PantallaEjercicios(
                navHostController = navHostController,
                bodyPart = bodyPart,
                equipo = equipo,
                paddingValues = paddingValues
            )
        }

        composable(
            route = "PantallaDetalleEjercicio/{ejercicioId}",
            arguments = listOf(
                navArgument("ejercicioId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val ejercicioId = backStackEntry.arguments?.getString("ejercicioId") ?: ""
            PantallaDetalleEjercicio(
                navHostController,
                ejercicioId,
                paddingValues = paddingValues
            )
        }

        composable("PantallaSeleccionDieta") {
            PantallaSeleccionDieta(
                navHostController = navHostController,
                paddingValues = paddingValues
            )
        }

        composable(
            "PantallaDieta/{objetivo}",
            arguments = listOf(navArgument("objetivo") { type = NavType.StringType })
        ) { backStackEntry ->
            val objetivo = backStackEntry.arguments?.getString("objetivo") ?: ""
            PantallaDieta(
                navHostController = navHostController,
                objetivoClave = objetivo,
                //paddingValues = paddingValues
            )
        }
    }
}
