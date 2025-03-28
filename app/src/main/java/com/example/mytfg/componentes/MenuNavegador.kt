package com.example.mytfg.componentes

import android.util.Log
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.mytfg.AuthManager
import com.example.mytfg.ui.theme.AzulOscuro

@Composable
fun MenuNavegador(navController: NavHostController, authManager: AuthManager) {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    Surface(
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        shadowElevation = 6.dp,
        modifier = Modifier.height(120.dp).padding(top = 10.dp)
    ) {
        NavigationBar(
            containerColor = AzulOscuro,
        ) {


            /*
            // FACTURAS EMITIDAS
            NavigationBarItem(
                icon = {
                    Icon(
                        Icons.AutoMirrored.Filled.receiptLong,
                        contentDescription = "Facturas-emitidas",
                        //tint = GrisOscuro2
                    )
                },
                label = {
                    Text(
                        "Fact. Emitidas",
                        //color = GrisOscuro2
                    )
                },
                selected = currentRoute == "PantallaFacturasEmitidas",
                onClick = {
                    navController.navigate("PantallaFacturasEmitidas") {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )

            // FACTURAS RECIBIDAS
            NavigationBarItem(
                icon = {
                    Icon(
                        Icons.Filled.RequestQuote,
                        contentDescription = "Facturas-recibidas",
                        //tint = GrisOscuro2
                    )
                },
                label = {
                    Text(
                        "Fact. Recibidas",
                        //color = GrisOscuro2
                    )
                },
                selected = currentRoute == "PantallaFacturasRecibidas",
                onClick = {
                    navController.navigate("PantallaFacturasRecibidas") {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )

             */


            // CIERRE DE SESIÓN
            NavigationBarItem(
                icon = {
                    Icon(
                        Icons.Filled.ExitToApp,
                        contentDescription = "Salir",
                        //tint = GrisOscuro2
                    )
                },
                label = {
                    Text(
                        "Salir",
                        //color = GrisOscuro2,
                    )
                },
                selected = false,
                onClick = {
                    authManager.logout(
                        onSuccess = {
                            navController.navigate("PantallaLogin") {
                                popUpTo(0) { inclusive = true }
                            }
                        },
                        onFailure = { exception ->
                            Log.e("MenuNavegador", "Error al cerrar sesión", exception)
                        }
                    )
                }
            )
        }
    }
}