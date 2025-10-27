package com.example.hikari.ui.theme.pantallas

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PantallaEstadoPedido() {
    var estadoPedido by remember { mutableStateOf("Pendiente") }

    Column {
        Text(text = "Estado del Pedido: $estadoPedido")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            estadoPedido = when (estadoPedido) {
                "Pendiente" -> "En Camino"
                "En Camino" -> "Entregado"
                else -> "Pendiente"
            }
        }) {
            Text("Actualizar Estado")
        }
    }
}