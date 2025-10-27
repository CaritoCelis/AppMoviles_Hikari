package com.example.hikari

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.example.hikari.modelo.ProductoEntity
import com.example.hikari.navegacion.RutasNavegacion
import com.example.hikari.repositorio.BaseDeDatosAplicacion
import com.example.hikari.repositorio.RepositorioCarrito
import com.example.hikari.repositorio.RepositorioProducto
import com.example.hikari.repositorio.RepositorioUsuario
import com.example.hikari.ui.theme.PasteleriaHikariTheme
import com.example.hikari.view_model.FabricaViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        crearCanalDeNotificaciones()

        // --- Inicialización Centralizada ---
        val baseDeDatos = BaseDeDatosAplicacion.obtenerBaseDeDatos(applicationContext)
        val repositorioUsuario = RepositorioUsuario(baseDeDatos.usuarioDao())
        val repositorioProducto = RepositorioProducto(baseDeDatos.productoDao())
        val repositorioCarrito = RepositorioCarrito(baseDeDatos.carritoDao())
        val fabricaViewModel = FabricaViewModel(repositorioUsuario, repositorioProducto, repositorioCarrito)

        // --- Población Segura de la Base de Datos ---
        lifecycleScope.launch {
            if (repositorioProducto.contar() == 0) {
                poblarBaseDeDatos(repositorioProducto)
            }
        }

        setContent {
            PasteleriaHikariTheme {
                val navController = rememberNavController()
                RutasNavegacion(
                    navController = navController,
                    fabricaViewModel = fabricaViewModel
                )
            }
        }
    }

    private fun crearCanalDeNotificaciones() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val nombre = "Pedidos"
            val descripcion = "Notificaciones sobre el estado de tus pedidos"
            val importancia = NotificationManager.IMPORTANCE_DEFAULT
            val canal = NotificationChannel("PEDIDOS_CANAL_ID", nombre, importancia).apply {
                description = descripcion
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(canal)
        }
    }

    private suspend fun poblarBaseDeDatos(repositorioProducto: RepositorioProducto) {
        val productos = listOf(
            ProductoEntity(
                nombre = "Torta de Cumpleaños",
                descripcion = "Clásica torta de bizcocho con relleno de manjar y crema, decorada para celebrar.",
                precio = 22000.0,
                imagen = "cumple"
            ),
            ProductoEntity(
                nombre = "Brownie de Chocolate",
                descripcion = "Intenso brownie de chocolate con nueces, húmedo y delicioso.",
                precio = 2500.0,
                imagen = "brownie"
            ),
            ProductoEntity(
                nombre = "Queque de Naranja",
                descripcion = "Esponjoso queque casero con un glaseado de naranja fresca.",
                precio = 8000.0,
                imagen = "naranja"
            ),
            ProductoEntity(
                nombre = "Empanada de Pino",
                descripcion = "Tradicional empanada chilena horneada, con pino de carne, cebolla y huevo.",
                precio = 2800.0,
                imagen = "empanada"
            ),
            ProductoEntity(
                nombre = "Galletas Surtidas",
                descripcion = "Docena de galletas artesanales: mantequilla, chips de chocolate y avena.",
                precio = 6000.0,
                imagen = "galletas"
            ),
            ProductoEntity(
                nombre = "Tiramisú",
                descripcion = "Postre italiano con capas de mascarpone y galletas mojadas en café.",
                precio = 4500.0,
                imagen = "tiramisu"
            ),
            ProductoEntity(
                nombre = "Torta de Vainilla",
                descripcion = "Suave bizcocho de vainilla, perfecto para acompañar un café a media tarde.",
                precio = 15000.0,
                imagen = "vainilla"
            ),
            ProductoEntity(
                nombre = "Mousse de Chocolate",
                descripcion = "Ligero y aireado mousse de chocolate bitter, un clásico que no falla.",
                precio = 3500.0,
                imagen = "mousse_chocolate"
            ),
            ProductoEntity(
                nombre = "Torta Vegana de Chocolate",
                descripcion = "Deliciosa torta de chocolate sin ingredientes de origen animal.",
                precio = 25000.0,
                imagen = "vegana_chocolate"
            ),
            ProductoEntity(
                nombre = "Torta Panqueque Chocolate",
                descripcion = "Torta de panqueque chocolate con un suave relleno de trufa.",
                precio = 20000.0,
                imagen = "cuadrada_chocolate"
            ),
            ProductoEntity(
                nombre = "Torta de Cumpleaños (8p)",
                descripcion = "Bizcocho de vainilla, relleno de manjar y cubierto de merengue.",
                precio = 18000.0,
                imagen = "cumple_8"
            ),
            ProductoEntity(
                nombre = "Torta de Cumpleaños (26p)",
                descripcion = "Gran torta de panqueque chocolate, rellena de trufa y frambuesa.",
                precio = 35000.0,
                imagen = "cumple_26"
            ),
            ProductoEntity(
                nombre = "Torta de Boda",
                descripcion = "Elegante torta de dos pisos, sabor a elección. Consulta para personalizar.",
                precio = 80000.0,
                imagen = "torta_boda"
            ),
            ProductoEntity(
                nombre = "Torta Manjar-Nuez",
                descripcion = "Clásica torta de bizcocho con abundante manjar y nueces.",
                precio = 19000.0,
                imagen = "torta_manjar"
            ),
            ProductoEntity(
                nombre = "Pan Sin Gluten",
                descripcion = "Hogaza de pan artesanal, ideal para personas con intolerancia al gluten.",
                precio = 4500.0,
                imagen = "pan_singluten"
            ),
            ProductoEntity(
                nombre = "Torta de Vainilla Especial",
                descripcion = "Torta de vainilla con crema pastelera y delicadas flores de azúcar.",
                precio = 17000.0,
                imagen = "torta_vainilla"
            ),
            ProductoEntity(
                nombre = "Cheesecake de Limón",
                descripcion = "Refrescante cheesecake con una intensa crema de limón y merengue.",
                precio = 16000.0,
                imagen = "cheesecake_limon"
            ),
            ProductoEntity(
                nombre = "Empanada de Manzana",
                descripcion = "Dulce empanada horneada, rellena de compota de manzana y canela.",
                precio = 2200.0,
                imagen = "empanada_manzana"
            ),
            ProductoEntity(
                nombre = "Torta de Aniversario",
                descripcion = "Torta especial para aniversarios, decorada con forma de corazón.",
                precio = 28000.0,
                imagen = "torta_aniversario"
            ),
            ProductoEntity(
                nombre = "Cheesecake de Arándanos",
                descripcion = "Suave cheesecake con mermelada casera de arándanos frescos.",
                precio = 18000.0,
                imagen = "cheesecake_arandano"
            ),
            ProductoEntity(
                nombre = "Cheesecake de Frambuesa",
                descripcion = "Cheesecake cremoso con una cubierta de frambuesas naturales.",
                precio = 19000.0,
                imagen = "cheesecake_frambueza"
            ),
            ProductoEntity(
                nombre = "Torta Cuadrada de Frutas",
                descripcion = "Torta de bizcocho rectangular, cubierta con crema y frutas de la estación.",
                precio = 21000.0,
                imagen = "torta_cuadrada_frutas"
            ),
            ProductoEntity(
                nombre = "Galletas Veganas de Avena",
                descripcion = "Galletas de avena y plátano, endulzadas con dátiles (Docena).",
                precio = 7000.0,
                imagen = "galletas_vegana_avena_1"
            ),
            ProductoEntity(
                nombre = "Mix de Galletas Premium",
                descripcion = "Caja con galletas de distintos sabores: red velvet, limón, y más (Docena).",
                precio = 9000.0,
                imagen = "galletas_distintos_sabores"
            )
        )
        repositorioProducto.insertarTodos(productos)
    }
}