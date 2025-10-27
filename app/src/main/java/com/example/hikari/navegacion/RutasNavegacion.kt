package com.example.hikari.navegacion

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.hikari.ui.theme.pantallas.*
import com.example.hikari.view_model.FabricaViewModel
import com.example.hikari.view_model.RegistroViewModel

@Composable
fun RutasNavegacion(
    navController: NavHostController,
    fabricaViewModel: FabricaViewModel
) {
    NavHost(navController = navController, startDestination = RutasPantalla.Inicio.ruta) {
        composable(RutasPantalla.Inicio.ruta) {
            PantallaInicio(
                alNavegarAInicioSesion = { navController.navigate(RutasPantalla.InicioSesion.ruta) },
                alNavegarARegistro = { navController.navigate(RutasPantalla.Registro.ruta) }
            )
        }
        composable(RutasPantalla.InicioSesion.ruta) {
            PantallaInicioSesion(
                alNavegarAInicio = { navController.navigate(RutasPantalla.ListaProductos.ruta) { popUpTo(RutasPantalla.Inicio.ruta) { inclusive = true } } },
                alNavegarARegistro = { navController.navigate(RutasPantalla.Registro.ruta) },
                alNavegarAtras = { navController.popBackStack() },
                viewModel = viewModel(factory = fabricaViewModel)
            )
        }
        composable(RutasPantalla.Registro.ruta) {
            val viewModel: RegistroViewModel = viewModel(factory = fabricaViewModel)
            PantallaRegistro(
                alNavegarAInicioSesion = {
                    navController.navigate(RutasPantalla.InicioSesion.ruta) {
                        popUpTo(navController.graph.startDestinationId) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                alNavegarAtras = { navController.popBackStack() },
                viewModel = viewModel
            )
        }
        composable(RutasPantalla.ListaProductos.ruta) {
            PantallaListaProductos(
                alNavegarACarrito = { navController.navigate(RutasPantalla.Carrito.ruta) },
                alNavegarADetalleProducto = { productoId -> navController.navigate(RutasPantalla.DetalleProducto.crearRuta(productoId)) },
                productoViewModel = viewModel(factory = fabricaViewModel),
                carritoViewModel = viewModel(factory = fabricaViewModel)
            )
        }
        composable(
            route = RutasPantalla.DetalleProducto.ruta,
            arguments = listOf(navArgument("productoId") { type = NavType.IntType })
        ) { backStackEntry ->
            val productoId = backStackEntry.arguments?.getInt("productoId") ?: 0
            PantallaDetalleProducto(
                productoId = productoId,
                productoViewModel = viewModel(factory = fabricaViewModel),
                carritoViewModel = viewModel(factory = fabricaViewModel),
                alNavegarAtras = { navController.popBackStack() }
            )
        }
        composable(RutasPantalla.Carrito.ruta) {
            PantallaCarrito(
                alNavegarAMisPedidos = { navController.navigate(RutasPantalla.MisPedidos.ruta) },
                alNavegarAtras = { navController.popBackStack() },
                carritoViewModel = viewModel(factory = fabricaViewModel)
            )
        }
        composable(RutasPantalla.MisPedidos.ruta) {
            PantallaMisPedidos(alNavegarAtras = { navController.popBackStack() })
        }
    }
}