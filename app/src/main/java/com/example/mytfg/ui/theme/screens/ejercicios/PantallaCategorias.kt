package com.example.mytfg.ui.theme.screens.ejercicios

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.mytfg.util.getApiBodyPart // Importa la función utilitaria

// Modelo para cada tipo de nivel
data class NivelEjercicio(
    val nivel: String,
    val imagenUrl: String
)

// Modelo para cada grupo muscular
data class CategoriaGrupo(
    val nombre: String,
    val niveles: List<NivelEjercicio>
)

@Composable
fun PantallaCategorias(navHostController: NavHostController) {
    // Estructura agrupada por parte del cuerpo
    val categorias = listOf(
        CategoriaGrupo(
            nombre = "Piernas superiores",
            niveles = listOf(
                NivelEjercicio("Principiante", "URL_IMAGEN"),
                NivelEjercicio("Medio", "URL_IMAGEN"),
                NivelEjercicio("Avanzado", "URL_IMAGEN")
            )
        ),
        CategoriaGrupo(
            nombre = "Piernas inferiores",
            niveles = listOf(
                NivelEjercicio("Principiante", "URL_IMAGEN"),
                NivelEjercicio("Medio", "URL_IMAGEN"),
                NivelEjercicio("Avanzado", "URL_IMAGEN")
            )
        ),
        CategoriaGrupo(
            nombre = "Cardio",
            niveles = listOf(
                NivelEjercicio("Principiante", "URL_IMAGEN"),
                NivelEjercicio("Medio", "URL_IMAGEN"),
                NivelEjercicio("Avanzado", "URL_IMAGEN")
            )
        ),
        // ...el resto de categorías
        CategoriaGrupo(
            nombre = "Torso",
            niveles = listOf(
                NivelEjercicio("Principiante", "https://images.pexels.com/photos/841130/pexels-photo-841130.jpeg"),
                NivelEjercicio("Medio", "https://images.pexels.com/photos/1139193/pexels-photo-1139193.jpeg"),
                NivelEjercicio("Avanzado", "https://images.pexels.com/photos/260352/pexels-photo-260352.jpeg")
            )
        ),
        CategoriaGrupo(
            nombre = "Brazos",
            niveles = listOf(
                NivelEjercicio("Principiante", "https://images.pexels.com/photos/841130/pexels-photo-841130.jpeg"),
                NivelEjercicio("Medio", "https://images.pexels.com/photos/1139193/pexels-photo-1139193.jpeg"),
                NivelEjercicio("Avanzado", "https://images.pexels.com/photos/260352/pexels-photo-260352.jpeg")
            )
        ),
        CategoriaGrupo(
            nombre = "Antebrazo",
            niveles = listOf(
                NivelEjercicio("Principiante", "https://images.pexels.com/photos/841130/pexels-photo-841130.jpeg"),
                NivelEjercicio("Medio", "https://images.pexels.com/photos/1139193/pexels-photo-1139193.jpeg"),
                NivelEjercicio("Avanzado", "https://images.pexels.com/photos/260352/pexels-photo-260352.jpeg")
            )
        ),
        CategoriaGrupo(
            nombre = "Espalda",
            niveles = listOf(
                NivelEjercicio("Principiante", "https://images.pexels.com/photos/841130/pexels-photo-841130.jpeg"),
                NivelEjercicio("Medio", "https://images.pexels.com/photos/1139193/pexels-photo-1139193.jpeg"),
                NivelEjercicio("Avanzado", "https://images.pexels.com/photos/260352/pexels-photo-260352.jpeg")
            )
        ),
    )

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        categorias.forEach { categoria ->
            item {
                Text(
                    text = categoria.nombre,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .padding(start = 16.dp, top = 24.dp, bottom = 8.dp)
                )
            }
            items(categoria.niveles) { nivel ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp)
                        .clickable {
                            // Navega pasando el nombre en inglés que espera la API
                            navHostController.navigate(
                                "PantallaEjercicios/${getApiBodyPart(categoria.nombre)}/${nivel.nivel}"
                            )
                        }
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsyncImage(
                            model = nivel.imagenUrl,
                            contentDescription = "${categoria.nombre} ${nivel.nivel}",
                            modifier = Modifier.size(80.dp)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = nivel.nivel,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }
        }
    }
}
