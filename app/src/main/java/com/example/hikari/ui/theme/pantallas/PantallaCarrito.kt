package com.example.hikari.ui.theme.pantallas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hikari.view_model.CarritoViewModel
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaCarrito(
    alNavegarAMisPedidos: () -> Unit,
    alNavegarAtras: () -> Unit,
    carritoViewModel: CarritoViewModel
) {
    val itemsCarrito by carritoViewModel.itemsCarritoConDetalles.collectAsState(initial = emptyList())
    val precioTotal = itemsCarrito.sumOf { it.precio * it.cantidad }

    val formatoMoneda = NumberFormat.getCurrencyInstance(Locale("es", "CL")).apply {
        maximumFractionDigits = 0
    }

    Scaffold(
        topBar = {
            BarraAplicacionHikari(
                titulo = "Carrito",
                puedeNavegarAtras = true,
                alNavegarAtras = alNavegarAtras
            )
        }
    ) { paddingInterno ->
        Column(modifier = Modifier.padding(paddingInterno).padding(16.dp)) {
            if (itemsCarrito.isEmpty()) {
                Text("Tu carrito está vacío.")
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    items(itemsCarrito) { item ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            elevation = CardDefaults.cardElevation(2.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(item.nombre, fontWeight = FontWeight.Bold)
                                    Text("Cantidad: ${item.cantidad}")
                                }
                                val precioFormateado =
                                    formatoMoneda.format(item.precio * item.cantidad)
                                Text(precioFormateado, fontWeight = FontWeight.SemiBold)
                                IconButton(onClick = { carritoViewModel.eliminarItem(item.productoId) }) {
                                    Icon(
                                        Icons.Default.Delete,
                                        contentDescription = "Eliminar producto"
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                Divider()
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Total:", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    val totalFormateado = formatoMoneda.format(precioTotal)
                    Text(totalFormateado, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Button(onClick = { carritoViewModel.limpiarCarrito() }) {
                        Text("Vaciar Carrito")
                    }
                    Button(onClick = alNavegarAMisPedidos) {
                        Text("Ir a Pagar")
                    }
                }
            }
        }
    }
}