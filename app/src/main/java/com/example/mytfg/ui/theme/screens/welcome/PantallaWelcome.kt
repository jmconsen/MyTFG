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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.example.mytfg.R
import com.example.mytfg.componentes.BotonEstandar

@Composable
fun PantallaWelcome(navHostController: NavHostController) {
    val images = listOf(
        R.drawable.image1,
        R.drawable.image2,
        R.drawable.image3,
        R.drawable.image4
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
