package com.example.mytfg.ui.theme.screens.dieta

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.mytfg.R
import com.example.mytfg.componentes.BotonEstandar
import com.example.mytfg.componentes.TopBar
import com.example.mytfg.ui.theme.NaranjaMuyClaro
import com.example.mytfg.ui.theme.Negro
import com.example.mytfg.viewmodel.DietaViewModel

@Composable
fun PantallaDieta(
    navHostController: NavHostController,
    objetivoClave: String,
    viewModel: DietaViewModel = viewModel()
) {
    val contenidoDieta by viewModel.contenidoDieta.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(objetivoClave) {
        viewModel.cargarDieta(objetivoClave)
    }

    val secciones = contenidoDieta?.split("\n\n") ?: listOf("Cargando dieta...")

    Scaffold(
        topBar = {
            TopBar(
                navHostController = navHostController,
                title = "Dieta: ${objetivoClave.replace('_', ' ').replaceFirstChar { it.uppercaseChar() }}"
            )
        }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Image(
                painter = painterResource(id = R.drawable.dietaensalada),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White.copy(alpha = 0.7f))
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(
                    text = objetivoClave.replace('_', ' ').replaceFirstChar { it.uppercaseChar() },
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = Negro,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                Surface(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    tonalElevation = 4.dp,
                    shape = MaterialTheme.shapes.medium
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(NaranjaMuyClaro)
                            .verticalScroll(rememberScrollState())
                            .padding(16.dp),
                    ) {
                        secciones.forEach { seccion ->
                            val partes = seccion.split(":", limit = 2)
                            if (partes.size == 2) {
                                val titulo = partes[0].trim()
                                val contenido = partes[1].trim()
                                Text(
                                    text = titulo,
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                    ),
                                    color = Negro,
                                    modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
                                )
                                Text(
                                    text = contenido,
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )
                            } else {
                                Text(
                                    text = seccion.trim(),
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier.padding(vertical = 8.dp)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                BotonEstandar(
                    texto = "Compartir dieta",
                    onClick = {
                        val textoCompartir = secciones.joinToString("\n\n")
                        val intent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(Intent.EXTRA_SUBJECT, "Mi dieta: ${objetivoClave.replace('_', ' ')}")
                            putExtra(Intent.EXTRA_TEXT, textoCompartir)
                        }
                        context.startActivity(Intent.createChooser(intent, "Compartir dieta con..."))
                    }
                )

                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}






/*
package com.example.mytfg.ui.theme.screens.dieta

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.mytfg.R
import com.example.mytfg.componentes.BotonEstandar
import com.example.mytfg.componentes.TopBar
import com.example.mytfg.ui.theme.NaranjaMuyClaro
import com.example.mytfg.ui.theme.NaranjaOscuro
import com.example.mytfg.ui.theme.Negro
import com.example.mytfg.viewmodel.DietaViewModel
import android.content.Intent
import androidx.compose.ui.platform.LocalContext


@Composable
fun PantallaDieta(
    navHostController: NavHostController,
    objetivoClave: String,
    viewModel: DietaViewModel = viewModel()
) {
    val contenidoDieta by viewModel.contenidoDieta.collectAsState()

    LaunchedEffect(objetivoClave) {
        viewModel.cargarDieta(objetivoClave)
    }

    val secciones = contenidoDieta?.split("\n\n") ?: listOf("Cargando dieta...")

    Scaffold(
        topBar = {
            TopBar(
                navHostController = navHostController,
                title = "Dieta: ${objetivoClave.replace('_', ' ').replaceFirstChar { it.uppercase() }}"
            )
        }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Image(
                painter = painterResource(id = R.drawable.dietaensalada),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White.copy(alpha = 0.7f))
            )

        Column(
            modifier = Modifier
                .fillMaxSize()

                .padding(16.dp)
        ) {
            Text(
                text = objetivoClave.replace('_', ' ').replaceFirstChar { it.uppercase() },
                //style = MaterialTheme.typography.headlineLarge,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = Negro,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            Surface(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                tonalElevation = 4.dp,
                shape = MaterialTheme.shapes.medium
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(NaranjaMuyClaro)
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp),
                ) {
                    secciones.forEach { seccion ->
                        val partes = seccion.split(":", limit = 2)
                        if (partes.size == 2) {
                            val titulo = partes[0].trim()
                            val contenido = partes[1].trim()
                            Text(
                                text = titulo,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    //fontSize = 20.sp,
                                ),
                                color = Negro,
                                modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
                            )
                            Text(
                                text = contenido,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                        } else {
                            Text(
                                text = seccion.trim(),
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

        }
            }
    }
}
*/
