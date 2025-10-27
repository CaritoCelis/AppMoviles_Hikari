package com.example.hikari.ui.theme.pantallas

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
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
fun PantallaDetalleProducto(
    productoId: Int,
    productoViewModel: ProductoViewModel,
    carritoViewModel: CarritoViewModel,
    alNavegarAtras: () -> Unit
) {
    val productos by productoViewModel.productos.collectAsState()
    val producto = productos.find { it.id == productoId }
    val contexto = LocalContext.current
    var cantidad by remember { mutableStateOf(1) }
    val estadoAnfitrionSnackbar = remember { SnackbarHostState() }
    val alcance = rememberCoroutineScope()

    Scaffold(
        topBar = { BarraAplicacionHikari(titulo = producto?.nombre ?: "Detalle", puedeNavegarAtras = true, alNavegarAtras = alNavegarAtras) },
        snackbarHost = { SnackbarHost(estadoAnfitrionSnackbar) }
    ) { paddingInterno ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingInterno)
                .padding(16.dp)
        ) {
            if (producto != null) {
                val idImagen = contexto.resources.getIdentifier(producto.imagen, "drawable", contexto.packageName)
                AsyncImage(
                    model = idImagen,
                    contentDescription = producto.nombre,
                    modifier = Modifier
                        .size(250.dp)
                        .align(Alignment.CenterHorizontally),
                    error = painterResource(id = android.R.drawable.ic_menu_gallery)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(producto.nombre, fontSize = 28.sp, fontWeight = FontWeight.Bold)
                Text(producto.descripcion, style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(8.dp))
                val precioFormateado = NumberFormat.getCurrencyInstance(Locale("es", "CL")).apply { maximumFractionDigits = 0 }.format(producto.precio)
                Text(precioFormateado, fontSize = 22.sp, fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(onClick = { if (cantidad > 1) cantidad-- }) {
                        Text("-")
                    }
                    Text("$cantidad", modifier = Modifier.padding(horizontal = 16.dp), fontSize = 20.sp)
                    Button(onClick = { cantidad++ }) {
                        Text("+")
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(
                        onClick = {
                            carritoViewModel.agregarAlCarrito(producto.id, cantidad)
                            alcance.launch {
                                estadoAnfitrionSnackbar.showSnackbar("Producto(s) añadido(s) al carrito")
                            }
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Añadir al carrito")
                    }
                    IconButton(onClick = {
                        val intent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(Intent.EXTRA_SUBJECT, "¡Mira este producto de Pastelería Hikari!")
                            putExtra(Intent.EXTRA_TEXT, "¡Hola! Te recomiendo este producto: ${producto.nombre} - ${producto.descripcion}. Búscalo en la app Pastelería Hikari.")
                        }
                        contexto.startActivity(Intent.createChooser(intent, "Compartir producto"))
                    }) {
                        Icon(Icons.Default.Share, contentDescription = "Compartir producto")
                    }
                }
            } else {
                Text("Producto no encontrado")
            }
        }
    }
}