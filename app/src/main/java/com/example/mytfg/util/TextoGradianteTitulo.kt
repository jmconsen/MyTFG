package com.example.mytfg.util

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.TileMode
import androidx.compose.animation.core.*
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun AnimatedGradientText(
    text: String,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 36.sp,
    fontWeight: FontWeight = FontWeight.ExtraBold,
) {
    val gradientColors = listOf(
        Color(0xFFFFCC80), // Naranja claro
        Color(0xFFFFB74D), // Naranja medio
        Color(0xFFFF9800), // Naranja fuerte
        Color(0xFFF57C00), // Naranja oscuro
        Color(0xFFEF6C00), // Más oscuro
        Color(0xFFE65100)  // Casi rojo
    )


    /*
    val gradientColors = listOf(
        Color(0xFF2196F3), // Azul brillante (energía, confianza)
        Color(0xFF21CBF3), // Azul claro (frescura)
        Color(0xFF4CAF50), // Verde (salud, vitalidad)
        Color(0xFF8BC34A), // Verde lima (motivación, juventud)
        Color(0xFFFFEB3B), // Amarillo (dinamismo, vitalidad)
        Color(0xFFFF9800)  // Naranja (entusiasmo, actividad)
    )

     */

    val infiniteTransition = rememberInfiniteTransition()
    val translateAnim = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f, // valor arbitrario grande para mover el gradiente
        animationSpec = infiniteRepeatable(
            animation = tween(6000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    val brush = Brush.linearGradient(
        colors = gradientColors,
        start = Offset(translateAnim.value, 0f),
        end = Offset(translateAnim.value + 300f, 0f),
        tileMode = TileMode.Mirror
    )

    Text(
        text = text,
        fontSize = fontSize,
        fontWeight = fontWeight,
        modifier = modifier,
        style = LocalTextStyle.current.copy(
            brush = brush
        ),
        textAlign = TextAlign.Center,
        color = Color.Unspecified // para que use el brush
    )
}
