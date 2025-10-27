package com.example.hikari.navegacion

sealed class RutasPantalla(val ruta: String) {
    data object Inicio : RutasPantalla("inicio")
    data object InicioSesion : RutasPantalla("inicio_sesion")
    data object Registro : RutasPantalla("registro")
    data object ListaProductos : RutasPantalla("lista_productos")
    data object Carrito : RutasPantalla("carrito")
    data object MisPedidos : RutasPantalla("mis_pedidos")
    data object DetalleProducto : RutasPantalla("detalle_producto/{productoId}") {
        fun crearRuta(productoId: Int) = "detalle_producto/$productoId"
    }
}