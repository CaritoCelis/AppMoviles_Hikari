package com.example.hikari.ui.theme.pantallas

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.hikari.R

data class ItemPedido(val nombre: String, val cantidad: Int, val precio: String)
data class Pedido(val id: String, val total: String, val estado: String, val items: List<ItemPedido>)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaMisPedidos(alNavegarAtras: () -> Unit) {
    val contexto = LocalContext.current

    // Launcher para solicitar el permiso de notificación
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted: Boolean ->
            if (isGranted) {
                // Permiso concedido, se podría mostrar una notificación de agradecimiento si se quisiera
            } else {
                // Permiso denegado
            }
        }
    )

    fun mostrarNotificacionDePedido(pedido: Pedido) {
        val notificationId = pedido.id.hashCode()
        val builder = NotificationCompat.Builder(contexto, "PEDIDOS_CANAL_ID")
            .setSmallIcon(R.drawable.logo) // Asegúrate de tener un ícono para la notificación
            .setContentTitle("Actualización de tu Pedido")
            .setContentText("¡Tu pedido ${pedido.id} está en camino!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(contexto)) {
            if (ContextCompat.checkSelfPermission(contexto, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                notify(notificationId, builder.build())
            }
        }
    }

    val pedidosDeEjemplo = listOf(
        Pedido("#12345", "$24.500 CLP", "Entregado", items = listOf(
            ItemPedido("Torta de Cumpleaños", 1, "$22.000 CLP"),
            ItemPedido("Brownie de Chocolate", 1, "$2.500 CLP")
        )),
        Pedido("#12366", "$18.000 CLP", "En Camino", items = listOf(
            ItemPedido("Cheesecake Frutos Rojos", 1, "$18.000 CLP")
        )),
        Pedido("#12378", "$8.000 CLP", "Pendiente", items = listOf(
            ItemPedido("Queque de Naranja", 1, "$8.000 CLP")
        ))
    )

    Scaffold(
        topBar = { BarraAplicacionHikari(titulo = "Mis Pedidos", puedeNavegarAtras = true, alNavegarAtras = alNavegarAtras) }
    ) { paddingInterno ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(paddingInterno).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(pedidosDeEjemplo) { pedido ->
                var estaExpandido by remember { mutableStateOf(false) }
                Card(
                    modifier = Modifier.fillMaxWidth().clickable { estaExpandido = !estaExpandido },
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Pedido ${pedido.id}", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                            Text(pedido.estado, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.primary)
                        }
                        Text("Total: ${pedido.total}", fontSize = 16.sp)

                        // Botón para notificar
                        Button(onClick = {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                when (PackageManager.PERMISSION_GRANTED) {
                                    ContextCompat.checkSelfPermission(contexto, Manifest.permission.POST_NOTIFICATIONS) -> {
                                        mostrarNotificacionDePedido(pedido)
                                    }
                                    else -> {
                                        requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                                    }
                                }
                            } else {
                                mostrarNotificacionDePedido(pedido) // En versiones antiguas no se necesita permiso
                            }
                        }) {
                            Text("Notificarme sobre este pedido")
                        }

                        AnimatedVisibility(visible = estaExpandido) {
                            Column(modifier = Modifier.padding(top = 16.dp)) {
                                Divider()
                                Spacer(modifier = Modifier.height(8.dp))
                                pedido.items.forEach {
                                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                        Text("${it.cantidad}x ${it.nombre}")
                                        Text(it.precio)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}