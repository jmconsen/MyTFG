package com.example.mytfg.ui.theme.screens.menu

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.mytfg.componentes.TopBarConUsuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.burnoutcrew.reorderable.*

import com.example.mytfg.R
import com.example.mytfg.componentes.BotonEstandar
import com.example.mytfg.componentes.MenuBoton
import com.example.mytfg.ui.theme.GrisOscuro2


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaMenu(navHostController: NavHostController, userName: String?, avatarUrl: String?) {
    val user = FirebaseAuth.getInstance().currentUser
    val db = FirebaseFirestore.getInstance()

    var userName by remember { mutableStateOf<String?>(null) }
    var avatarUrl by remember { mutableStateOf<String?>(null) }

    val defaultAvatar = "https://cdn-icons-png.flaticon.com/512/149/149071.png"

    // Definimos los botones base por si no hay ninguno guardado
    val botonesBase = listOf(
        MenuBoton("Entrenamiento con IA", "PantallaFormularioIA"),
        MenuBoton("Ejercicios", "PantallaCategorias"),
        MenuBoton("Perfil", "PantallaEditarPerfil"),
        MenuBoton("Dietas", "PantallaSeleccionDieta")
    )

    // Estado de los botones y su orden
    val botones = remember { mutableStateListOf<MenuBoton>() }
    var botonesCargados by remember { mutableStateOf(false) }

    // Cargar orden de botones del usuario desde Firestore
    LaunchedEffect(user) {
        user?.let { u ->
            db.collection("usuarios").document(u.uid).get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        userName = document.getString("nombre")
                        avatarUrl = document.getString("avatarUrl")
                    }

                    val orden = document.get("menuOrden") as? List<*>

                    if (orden != null && orden.all { it is String }) {
                        val ordenado = orden.mapNotNull { nombre ->
                            botonesBase.find { it.titulo == nombre }
                        }
                        botones.clear()
                        botones.addAll(ordenado.ifEmpty { botonesBase })
                    } else {
                        botones.clear()
                        botones.addAll(botonesBase)
                    }
                    botonesCargados = true
                }
                .addOnFailureListener {
                    botones.clear()
                    botones.addAll(botonesBase)
                    botonesCargados = true
                }
        }
    }

    // Reordenamiento con Reorderable para permitir moverlos con drag & drop
    val reorderableState = rememberReorderableLazyListState(
        onMove = { from, to ->
            botones.move(from.index, to.index)

            // Guardar nuevo orden en Firestore
            user?.let { u ->
                val nuevoOrden = botones.map { it.titulo }
                db.collection("usuarios").document(u.uid)
                    .update("menuOrden", nuevoOrden)
                    .addOnFailureListener { e ->
                        Log.e("PantallaMenu", "Error al guardar orden: ${e.message}")
                    }
            }
        }
    )

    //Principal Scaffold con TopBar y fondo
    Scaffold(
        topBar = {
            if (userName != null) {
                TopBarConUsuario(
                    userName = userName!!,
                    avatarUrl = avatarUrl ?: defaultAvatar,
                    onProfileClick = { navHostController.navigate("PantallaUnoPerfil") }
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            // Imagen de fondo
            Image(
                painter = painterResource(id = R.drawable.pantalla_menu),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // Capa blanca translúcida
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White.copy(alpha = 0.7f))
            )

            //Mostramos solo los botones cuando se han cargado
            if (botonesCargados) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(64.dp))

                    //Título del menú
                    Text(
                        text = "Menú Principal",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = GrisOscuro2,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    Spacer(modifier = Modifier.height(64.dp))

                    // Lista reordenable de botones
                    LazyColumn(
                        state = reorderableState.listState,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp)
                            .reorderable(reorderableState)
                            .detectReorderAfterLongPress(reorderableState),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(bottom = 48.dp)
                    ) {
                        items(botones, key = { it.titulo }) { item ->
                            ReorderableItem(reorderableState, key = item.titulo) { isDragging ->
                                val elevation = if (isDragging) 8.dp else 2.dp
                                Card(
                                    modifier = Modifier.fillMaxWidth(),
                                    elevation = CardDefaults.cardElevation(elevation),
                                    colors = CardDefaults.cardColors(containerColor = Color.White)
                                ) {
                                    BotonEstandar(
                                        texto = item.titulo,
                                        onClick = { navHostController.navigate(item.ruta) },
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

//Función para ordenar los botones del menú
data class MenuItem(val titulo: String, val ruta: String)

fun <T> MutableList<T>.move(fromIndex: Int, toIndex: Int) {
    if (fromIndex == toIndex) return
    val item = removeAt(fromIndex)
    add(toIndex, item)
}

/*
@Composable
fun BotonEstandar(
    texto: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .height(56.dp)
            .fillMaxWidth(),
        shape = MaterialTheme.shapes.medium
    ) {
        Text(texto, fontSize = 18.sp)
    }
}

 */



/*package com.example.mytfg.ui.theme.screens.menu

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mytfg.R
import com.example.mytfg.componentes.BotonEstandar
import com.example.mytfg.componentes.TopBarConUsuario
import com.example.mytfg.ui.theme.GrisOscuro2
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun PantallaMenu(navHostController: NavHostController, userName: String?, avatarUrl: String?) {
    val user = FirebaseAuth.getInstance().currentUser
    val db = FirebaseFirestore.getInstance()

    var userName by remember { mutableStateOf<String?>(null) }
    var avatarUrl by remember { mutableStateOf<String?>(null) }

    val defaultAvatar = "https://cdn-icons-png.flaticon.com/512/149/149071.png"

    LaunchedEffect(user) {
        user?.let {
            db.collection("usuarios").document(it.uid).get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        userName = document.getString("nombre")
                        avatarUrl = document.getString("avatarUrl")
                    }
                }
                .addOnFailureListener { e ->
                    Log.e("PantallaMenu", "Error al leer: ${e.message}")
                }
        }
    }

    Scaffold(
        topBar = {
            if (userName != null) {
                TopBarConUsuario(
                    userName = userName!!,
                    avatarUrl = avatarUrl ?: defaultAvatar,
                    onProfileClick = { navHostController.navigate("PantallaUnoPerfil") }
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Imagen de fondo
            Image(
                painter = painterResource(id = R.drawable.pantalla_menu),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // Capa blanca translúcida
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White.copy(alpha = 0.7f))
            )

            // Contenido del menú
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(32.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .padding(vertical = 32.dp, horizontal = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Spacer(modifier = Modifier.height(32.dp))
                        Text(
                            text = "Menú Principal",
                            fontSize = 40.sp,
                            fontWeight = FontWeight.Bold,
                            color = GrisOscuro2,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(bottom = 24.dp)
                        )

                        BotonEstandar(
                            texto = "Entrenamiento con IA",
                            onClick = { navHostController.navigate("PantallaFormularioIA") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        BotonEstandar(
                            texto = "Ejercicios",
                            onClick = { navHostController.navigate("PantallaCategorias") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        BotonEstandar(
                            texto = "Perfil",
                            onClick = { navHostController.navigate("PantallaEditarPerfil") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        BotonEstandar(
                            texto = "Dietas",
                            onClick = { navHostController.navigate("PantallaSeleccionDieta") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                        )
                    }
                }
            }
        }
    }
}
*/
