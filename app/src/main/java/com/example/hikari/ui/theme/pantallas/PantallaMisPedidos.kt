package com.example.hikari.ui.theme.pantallas

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.hikari.R
import com.example.hikari.ui.theme.*

data class ItemPedido(val nombre: String, val cantidad: Int, val precio: String)
data class Pedido(val id: String, val total: String, val estado: String, val items: List<ItemPedido>)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaMisPedidos(alNavegarAtras: () -> Unit) {
    val contexto = LocalContext.current

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted: Boolean ->
            // Permiso manejado
        }
    )

    fun mostrarNotificacionDePedido(pedido: Pedido) {
        val notificationId = pedido.id.hashCode()
        val builder = NotificationCompat.Builder(contexto, "PEDIDOS_CANAL_ID")
            .setSmallIcon(R.drawable.logo)
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
        topBar = {
            BarraAplicacionHikari(
                titulo = "Mis Pedidos",
                puedeNavegarAtras = true,
                alNavegarAtras = alNavegarAtras
            )
        },
        containerColor = HikariWhite
    ) { paddingInterno ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(HikariLightPink, HikariWhite)
                    )
                )
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingInterno)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    // Header decorativo
                    Text(
                        text = "Historial de pedidos",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = HikariOnSurface,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                items(pedidosDeEjemplo) { pedido ->
                    var estaExpandido by remember { mutableStateOf(false) }

                    // Determinar color según estado
                    val colorEstado = when (pedido.estado) {
                        "Entregado" -> HikariSuccess
                        "En Camino" -> HikariPeach
                        "Pendiente" -> HikariWarning
                        else -> HikariLightPink
                    }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(6.dp, RoundedCornerShape(20.dp))
                            .clickable { estaExpandido = !estaExpandido },
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = HikariWhite),
                        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                    ) {
                        Column(modifier = Modifier.padding(20.dp)) {
                            // Header del pedido
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "Pedido ${pedido.id}",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 20.sp,
                                        color = HikariOnSurface
                                    )

                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.padding(top = 4.dp)
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(10.dp)
                                                .clip(CircleShape)
                                                .background(colorEstado)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = pedido.estado,
                                            fontWeight = FontWeight.SemiBold,
                                            fontSize = 14.sp,
                                            color = HikariOnSurfaceLight
                                        )
                                    }
                                }

                                // Badge de total
                                Surface(
                                    shape = RoundedCornerShape(12.dp),
                                    color = HikariPink,
                                    shadowElevation = 4.dp
                                ) {
                                    Text(
                                        text = pedido.total,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = HikariWhite,
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            // Botón de notificación decorado
                            Button(
                                onClick = {
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
                                        mostrarNotificacionDePedido(pedido)
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(48.dp),
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = HikariLavender
                                )
                            ) {
                                Icon(
                                    Icons.Default.Notifications,
                                    contentDescription = null,
                                    tint = HikariPink,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Notificarme",
                                    color = HikariPink,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }

                            // Botón expandir/contraer
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 12.dp),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                TextButton(onClick = { estaExpandido = !estaExpandido }) {
                                    Text(
                                        text = if (estaExpandido) "Ver menos" else "Ver detalles",
                                        color = HikariPink,
                                        fontWeight = FontWeight.Medium
                                    )
                                    Icon(
                                        imageVector = if (estaExpandido) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                                        contentDescription = null,
                                        tint = HikariPink
                                    )
                                }
                            }

                            // Detalles expandibles
                            AnimatedVisibility(
                                visible = estaExpandido,
                                enter = expandVertically() + fadeIn(),
                                exit = shrinkVertically() + fadeOut()
                            ) {
                                Column(modifier = Modifier.padding(top = 16.dp)) {
                                    Divider(
                                        color = HikariPink.copy(alpha = 0.3f),
                                        thickness = 1.dp
                                    )
                                    Spacer(modifier = Modifier.height(16.dp))

                                    Text(
                                        text = "Productos:",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = HikariOnSurface,
                                        modifier = Modifier.padding(bottom = 8.dp)
                                    )

                                    pedido.items.forEach { item ->
                                        Surface(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(vertical = 4.dp),
                                            shape = RoundedCornerShape(12.dp),
                                            color = HikariLightPink
                                        ) {
                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(12.dp),
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Row(verticalAlignment = Alignment.CenterVertically) {
                                                    Surface(
                                                        shape = CircleShape,
                                                        color = HikariPink,
                                                        modifier = Modifier.size(28.dp)
                                                    ) {
                                                        Box(contentAlignment = Alignment.Center) {
                                                            Text(
                                                                text = "${item.cantidad}",
                                                                color = HikariWhite,
                                                                fontWeight = FontWeight.Bold,
                                                                fontSize = 14.sp
                                                            )
                                                        }
                                                    }
                                                    Spacer(modifier = Modifier.width(12.dp))
                                                    Text(
                                                        text = item.nombre,
                                                        fontSize = 14.sp,
                                                        color = HikariOnSurface
                                                    )
                                                }
                                                Text(
                                                    text = item.precio,
                                                    fontWeight = FontWeight.SemiBold,
                                                    color = HikariPink,
                                                    fontSize = 14.sp
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
        }
    }
}