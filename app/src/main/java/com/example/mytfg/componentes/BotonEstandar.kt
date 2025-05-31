package com.example.mytfg.componentes

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mytfg.ui.theme.Blanco
import com.example.mytfg.ui.theme.Naranja
import com.example.mytfg.ui.theme.NaranjaClaro
import com.example.mytfg.ui.theme.NaranjaMedio
import com.example.mytfg.ui.theme.Negro

@Composable
fun BotonEstandar(
    texto: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp)
        .height(56.dp)
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = NaranjaMedio,
            contentColor = Negro
        ),
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        contentPadding = PaddingValues(
            start = 8.dp,
            top = 16.dp,
            end = 8.dp,
            bottom = 16.dp
        )
    ) {
        Text(
            text = texto,
            fontSize = 18.sp
        )
    }
}
