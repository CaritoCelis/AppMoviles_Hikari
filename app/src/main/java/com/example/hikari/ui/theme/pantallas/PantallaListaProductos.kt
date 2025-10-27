package com.example.hikari.ui.theme.pantallas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.hikari.view_model.CarritoViewModel
import com.example.hikari.view_model.ProductoViewModel
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaListaProductos(
    alNavegarACarrito: () -> Unit,
    alNavegarADetalleProducto: (Int) -> Unit,
    productoViewModel: ProductoViewModel,
    carritoViewModel: CarritoViewModel
) {
    val productos by productoViewModel.productos.collectAsState()
    val contexto = LocalContext.current
    val estadoAnfitrionSnackbar = remember { SnackbarHostState() }
    val alcance = rememberCoroutineScope()

    Scaffold(
        topBar = { BarraAplicacionHikari(titulo = "Catálogo", puedeNavegarAtras = false, alNavegarAtras = {}) },
        snackbarHost = { SnackbarHost(estadoAnfitrionSnackbar) }
    ) { paddingInterno ->
        Column(modifier = Modifier.padding(paddingInterno).padding(horizontal = 8.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                Button(onClick = alNavegarACarrito) {
                    Text("Ver Carrito")
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(productos) { producto ->
                    Card(
                        onClick = { alNavegarADetalleProducto(producto.id) },
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            val idImagen = contexto.resources.getIdentifier(producto.imagen, "drawable", contexto.packageName)
                            AsyncImage(
                                model = idImagen,
                                contentDescription = producto.nombre,
                                modifier = Modifier.size(100.dp),
                                error = painterResource(id = android.R.drawable.ic_menu_gallery)
                            )
                            Column(modifier = Modifier.padding(start = 16.dp).weight(1f)) {
                                Text(producto.nombre, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                                Text(producto.descripcion, style = MaterialTheme.typography.bodyMedium, maxLines = 2)
                                val precioFormateado = NumberFormat.getCurrencyInstance(Locale("es", "CL")).apply { maximumFractionDigits = 0 }.format(producto.precio)
                                Text(precioFormateado, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                            }
                            Button(onClick = {
                                carritoViewModel.agregarAlCarrito(producto.id, 1)
                                alcance.launch {
                                    estadoAnfitrionSnackbar.showSnackbar("Producto añadido al carrito")
                                }
                            }) {
                                Text("Añadir")
                            }
                        }
                    }
                }
            }
        }
    }
}