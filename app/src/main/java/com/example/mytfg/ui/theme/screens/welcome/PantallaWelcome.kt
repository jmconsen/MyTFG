import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.example.mytfg.R
import com.example.mytfg.componentes.BotonEstandar
import com.example.mytfg.ui.theme.GrisOscuro2
import com.example.mytfg.ui.theme.Naranja

@Composable
fun PantallaWelcome(navHostController: NavHostController) {
    val images = listOf(
        R.drawable.image6,
        R.drawable.image7,
        R.drawable.image8,
        R.drawable.image9
    )
    val titles = listOf(
        "Bienvenido",
        "Planes de ejercicios personalizados",
        "Amplia biblioteca de ejercicios",
        "Métricas de salud y análisis de ejercicios"
    )
    val descriptions = listOf(
        "A tu asesor físico personal",
        "Elige tu propio camino hacia el fitness con IA. \uD83C\uDFCB\uFE0F\u200D♀\uFE0F",
        "¡Explora más de 100.000 ejercicios diseñados para ti! \uD83D\uDCAA",
        "Monitorea tu perfil de salud fácilmente. \uD83D\uDCC8"
    )
    var currentIndex by remember { mutableStateOf(0) }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Imagen a pantalla completa
        Image(
            painter = painterResource(id = images[currentIndex]),
            contentDescription = "Imagen de bienvenida",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Título y descripción
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(100.dp))
            Text(
                text = titles[currentIndex],
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = White,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 16.dp),
                style = TextStyle(
                    shadow = Shadow(
                        color = Naranja,
                        offset = Offset(5f, 5f),
                        blurRadius = 3f
                    )
                )
            )

            Text(
                text = descriptions[currentIndex],
                fontSize = 20.sp,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp),
                style = TextStyle(
                    shadow = Shadow(
                        color = Naranja,
                        offset = Offset(5f, 5f),
                        blurRadius = 3f
                    )
                )
            )
        }

        // Botón Login solo en la última imagen (SE SUPONE SOBRE LA IMAGEN)
        if (currentIndex == images.size - 1) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 64.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                BotonEstandar(
                    texto = "Login",
                    onClick = { navHostController.navigate("PantallaLogin") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .padding(bottom = 64.dp),
                )
            }
        }

        // Flecha Izquierda
        if (currentIndex > 0) {
            Box(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 16.dp)
                    .background(Color.White, shape = RoundedCornerShape(50))
                    .clickable { currentIndex-- }
                    .padding(12.dp)
            ) {
                Text(
                    text = "<",
                    fontSize = 32.sp,
                    color = Color.Black
                )
            }
        }

        // Flecha Derecha
        if (currentIndex < images.size - 1) {
            Box(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 16.dp)
                    .background(Color.White, shape = RoundedCornerShape(50))
                    .clickable { currentIndex++ }
                    .padding(12.dp)
            ) {
                Text(
                    text = ">",
                    fontSize = 32.sp,
                    color = Color.Black
                )
            }
        }
    }
}
