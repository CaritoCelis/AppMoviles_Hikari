package com.example.hikari.ui.theme.pantallas

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.hikari.ui.theme.*
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
        topBar = {
            BarraAplicacionHikari(
                titulo = producto?.nombre ?: "Detalle",
                puedeNavegarAtras = true,
                alNavegarAtras = alNavegarAtras
            )
        },
        snackbarHost = { SnackbarHost(estadoAnfitrionSnackbar) },
        containerColor = HikariWhite
    ) { paddingInterno ->
        if (producto != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(paddingInterno)
            ) {
                // Contenedor de imagen con gradiente decorativo
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(320.dp)
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(HikariLightPink, HikariWhite)
                            )
                        )
                ) {
                    val idImagen = contexto.resources.getIdentifier(producto.imagen, "drawable", contexto.packageName)
                    AsyncImage(
                        model = idImagen,
                        contentDescription = producto.nombre,
                        modifier = Modifier
                            .size(280.dp)
                            .align(Alignment.Center)
                            .clip(RoundedCornerShape(24.dp))
                            .shadow(8.dp, RoundedCornerShape(24.dp)),
                        error = painterResource(id = android.R.drawable.ic_menu_gallery)
                    )
                }

                // Contenido principal
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                ) {
                    // Título del producto
                    Text(
                        text = producto.nombre,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = HikariOnSurface,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    // Precio en un badge decorativo
                    Surface(
                        modifier = Modifier.padding(bottom = 16.dp),
                        shape = RoundedCornerShape(12.dp),
                        color = HikariPink,
                        shadowElevation = 4.dp
                    ) {
                        val precioFormateado = NumberFormat.getCurrencyInstance(Locale("es", "CL"))
                            .apply { maximumFractionDigits = 0 }
                            .format(producto.precio)
                        Text(
                            text = precioFormateado,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = HikariWhite,
                            modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)
                        )
                    }

                    // Descripción con fondo decorativo
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 24.dp),
                        shape = RoundedCornerShape(16.dp),
                        color = HikariCream
                    ) {
                        Text(
                            text = producto.descripcion,
                            style = MaterialTheme.typography.bodyLarge,
                            color = HikariOnSurfaceLight,
                            modifier = Modifier.padding(16.dp),
                            lineHeight = 24.sp
                        )
                    }

                    // Selector de cantidad decorado
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 24.dp),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = HikariLightPink),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Text(
                                text = "Cantidad:",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = HikariOnSurface
                            )

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                // Botón menos
                                IconButton(
                                    onClick = { if (cantidad > 1) cantidad-- },
                                    modifier = Modifier
                                        .size(48.dp)
                                        .background(HikariPink, CircleShape)
                                        .shadow(4.dp, CircleShape)
                                ) {
                                    Text(
                                        text = "−",
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = HikariWhite
                                    )
                                }

                                // Número de cantidad
                                Surface(
                                    shape = RoundedCornerShape(12.dp),
                                    color = HikariWhite,
                                    shadowElevation = 2.dp
                                ) {
                                    Text(
                                        text = "$cantidad",
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = HikariPink,
                                        modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
                                    )
                                }

                                // Botón más
                                IconButton(
                                    onClick = { cantidad++ },
                                    modifier = Modifier
                                        .size(48.dp)
                                        .background(HikariPink, CircleShape)
                                        .shadow(4.dp, CircleShape)
                                ) {
                                    Text(
                                        text = "+",
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = HikariWhite
                                    )
                                }
                            }
                        }
                    }

                    // Botones de acción decorados
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Botón añadir al carrito
                        Button(
                            onClick = {
                                carritoViewModel.agregarAlCarrito(producto.id, cantidad)
                                alcance.launch {
                                    estadoAnfitrionSnackbar.showSnackbar("✨ Producto(s) añadido(s) al carrito")
                                }
                            },
                            modifier = Modifier
                                .weight(1f)
                                .height(56.dp)
                                .shadow(8.dp, RoundedCornerShape(16.dp)),
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = HikariPinkDark
                            )
                        ) {
                            Icon(
                                Icons.Default.ShoppingCart,
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Añadir al carrito",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                        // Botón compartir
                        IconButton(
                            onClick = {
                                val intent = Intent(Intent.ACTION_SEND).apply {
                                    type = "text/plain"
                                    putExtra(Intent.EXTRA_SUBJECT, "¡Mira este producto de Pastelería Hikari!")
                                    putExtra(Intent.EXTRA_TEXT, "¡Hola! Te recomiendo este producto: ${producto.nombre} - ${producto.descripcion}. Búscalo en la app Pastelería Hikari.")
                                }
                                contexto.startActivity(Intent.createChooser(intent, "Compartir producto"))
                            },
                            modifier = Modifier
                                .size(56.dp)
                                .background(HikariLavender, RoundedCornerShape(16.dp))
                                .shadow(4.dp, RoundedCornerShape(16.dp))
                        ) {
                            Icon(
                                Icons.Default.Share,
                                contentDescription = "Compartir producto",
                                tint = HikariPinkDark
                            )
                        }
                    }
                }
            }
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Producto no encontrado",
                    style = MaterialTheme.typography.bodyLarge,
                    color = HikariOnSurfaceLight
                )
            }
        }
    }
}