package com.example.mytfg.ui.theme.screens.menu

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mytfg.R
import com.example.mytfg.componentes.BotonEstandar
import com.example.mytfg.componentes.MenuBoton
import com.example.mytfg.componentes.TopBarConUsuario
import com.example.mytfg.ui.theme.Naranja
import com.example.mytfg.ui.theme.NaranjaMuyClaro
import com.example.mytfg.ui.theme.NaranjaOscuro
import com.example.mytfg.util.AnimatedGradientText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import org.burnoutcrew.reorderable.*

@Composable
fun PantallaMenu(navHostController: NavHostController, userName: String?, avatarUrl: String?) {
    val user = FirebaseAuth.getInstance().currentUser
    val db = FirebaseFirestore.getInstance()

    var userNameState by remember { mutableStateOf(userName) }
    var avatarUrlState by remember { mutableStateOf(avatarUrl) }

    val defaultAvatar = "https://cdn-icons-png.flaticon.com/512/149/149071.png"

    val botonesBase = listOf(
        MenuBoton("Entrenamiento con IA", "PantallaFormularioIA"),
        MenuBoton("Ejercicios", "PantallaCategorias"),
        MenuBoton("Perfil", "PantallaEditarPerfil"),
        MenuBoton("Dietas", "PantallaSeleccionDieta")
    )

    val colores = listOf(
        Color(0xFFFFCC80),
        Color(0xFFFFB74D),
        Color(0xFFFF9800),
        Color(0xFFF57C00),
        Color(0xFFEF6C00),
        Color(0xFFE65100)
    )

    val colorAnim = remember { Animatable(colores[0]) }
    var currentIndex by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            val nextIndex = (currentIndex + 1) % colores.size
            colorAnim.animateTo(
                targetValue = colores[nextIndex],
                animationSpec = tween(durationMillis = 3000)
            )
            currentIndex = nextIndex
        }
    }

    val botones = remember { mutableStateListOf<MenuBoton>() }
    var botonesCargados by remember { mutableStateOf(false) }

    LaunchedEffect(user) {
        user?.let { u ->
            db.collection("usuarios").document(u.uid).get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        userNameState = document.getString("nombre")
                        avatarUrlState = document.getString("avatarUrl")
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

    val reorderableState = rememberReorderableLazyListState(
        onMove = { from, to ->
            botones.move(from.index, to.index)
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

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            if (userNameState != null) {
                TopBarConUsuario(
                    userName = userNameState!!,
                    avatarUrl = avatarUrlState ?: defaultAvatar,
                    onProfileClick = { showDialog = true }
                )
            }

            //Formato del diálogo de seleccionar de plan
            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = { Text("¿Qué plan quieres ver?") },
                    text = {
                        Text(
                            "Selecciona el tipo de plan que deseas visualizar:",
                            fontSize = 18.sp
                        )
                   },
                    confirmButton = {
                        TextButton(onClick = {
                            showDialog = false
                            scope.launch {
                                user?.let { u ->
                                    db.collection("usuarios").document(u.uid).get()
                                        .addOnSuccessListener { document ->
                                            val planEntrenamiento = document.getString("planEntrenamiento")
                                            if (!planEntrenamiento.isNullOrBlank()) {
                                                navHostController.navigate("PantallaPlanEntrenamientoIA/$planEntrenamiento")
                                            } else {
                                                Toast.makeText(context, "No hay plan de entrenamiento generado", Toast.LENGTH_SHORT).show()
                                            }
                                        }
                                        .addOnFailureListener {
                                            Toast.makeText(context, "Error al obtener el plan", Toast.LENGTH_SHORT).show()
                                        }
                                }
                            }
                        }) {
                            Text(
                                text = "Entrenamiento",
                                color = NaranjaOscuro,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = {
                            showDialog = false
                            scope.launch {
                                user?.let { u ->
                                    db.collection("usuarios").document(u.uid).get()
                                        .addOnSuccessListener { document ->
                                            val planDieta = document.getString("planDieta")
                                            if (!planDieta.isNullOrBlank()) {
                                                navHostController.navigate("PantallaPlanDietaIA/${Uri.encode(planDieta)}")
                                            } else {
                                                Toast.makeText(context, "No hay plan de dieta generado", Toast.LENGTH_SHORT).show()
                                            }
                                        }
                                        .addOnFailureListener {
                                            Toast.makeText(context, "Error al obtener el plan de dieta", Toast.LENGTH_SHORT).show()
                                        }
                                }
                            }
                        }) {
                            Text(
                                text = "Dieta",
                                color = NaranjaOscuro,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        }
                    },
                    containerColor = NaranjaMuyClaro
                )
            }

        }

    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Image(
                painter = painterResource(id = R.drawable.pantalla_menu),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White.copy(alpha = 0.6f))
            )

            if (botonesCargados) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(64.dp))

                    AnimatedGradientText(
                        text = "Menú Principal",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp)
                    )

                    Spacer(modifier = Modifier.height(48.dp))

                    LazyColumn(
                        state = reorderableState.listState,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .reorderable(reorderableState),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(top = 8.dp, bottom = 8.dp)
                    ) {
                        items(botones, key = { it.titulo }) { item ->
                            ReorderableItem(reorderableState, key = item.titulo) { _ ->
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(56.dp)
                                        .padding(horizontal = 8.dp)
                                        .detectReorderAfterLongPress(reorderableState)
                                ) {
                                    BotonEstandar(
                                        texto = item.titulo,
                                        onClick = { navHostController.navigate(item.ruta) },
                                        modifier = Modifier.weight(1f)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            /*
            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = { Text("¿Qué plan quieres ver?") },
                    text = { Text("Selecciona el tipo de plan que deseas visualizar:") },
                    confirmButton = {
                        TextButton(onClick = {
                            showDialog = false
                            scope.launch {
                                user?.let { u ->
                                    db.collection("usuarios").document(u.uid).get()
                                        .addOnSuccessListener { document ->
                                            val planEntrenamiento = document.getString("planEntrenamiento")
                                            if (!planEntrenamiento.isNullOrBlank()) {
                                                navHostController.navigate("PantallaPlanGenerado/$planEntrenamiento")
                                            } else {
                                                Toast.makeText(context, "No hay plan de entrenamiento generado", Toast.LENGTH_SHORT).show()
                                            }
                                        }
                                        .addOnFailureListener {
                                            Toast.makeText(context, "Error al obtener el plan", Toast.LENGTH_SHORT).show()
                                        }
                                }
                            }
                        }) {
                            Text("Entrenamiento")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = {
                            showDialog = false
                            scope.launch {
                                user?.let { u ->
                                    db.collection("usuarios").document(u.uid).get()
                                        .addOnSuccessListener { document ->
                                            val planDieta = document.getString("planDieta")
                                            if (!planDieta.isNullOrBlank()) {
                                                navHostController.navigate("PantallaPlanDietaIA/${Uri.encode(planDieta)}")
                                            } else {
                                                Toast.makeText(context, "No hay plan de dieta generado", Toast.LENGTH_SHORT).show()
                                            }
                                        }
                                        .addOnFailureListener {
                                            Toast.makeText(context, "Error al obtener el plan de dieta", Toast.LENGTH_SHORT).show()
                                        }
                                }
                            }
                        }) {
                            Text("Dieta")
                        }
                    }
                )
            }
            */
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
