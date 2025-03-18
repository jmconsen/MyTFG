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
import com.example.mytfg.ui.theme.AzulClaro
import com.example.mytfg.ui.theme.Blanco
import com.example.mytfg.ui.theme.Negro

@Composable
fun BotonEstandar(
    texto: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp)
        .height(64.dp)
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Negro,
            contentColor = Blanco
        ),
        modifier = modifier,
        shape = RoundedCornerShape(16.dp), // Ajusta este valor para cambiar la redondez
        contentPadding = PaddingValues(
            start = 16.dp,
            top = 4.dp,
            end = 16.dp,
            bottom = 4.dp
        )
    ) {
        Text(
            text = texto,
            fontSize = 16.sp
        )
    }
}