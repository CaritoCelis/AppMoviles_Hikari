package com.example.hikari.repositorio

import com.example.hikari.modelo.CarritoDao
import com.example.hikari.modelo.CarritoEntity
import com.example.hikari.modelo.DetallesItemCarrito
import kotlinx.coroutines.flow.Flow

class RepositorioCarrito(private val carritoDao: CarritoDao) {
    val itemsCarritoConDetalles: Flow<List<DetallesItemCarrito>> = carritoDao.obtenerItemsCarritoConDetalles()

    suspend fun agregarAlCarrito(productoId: Int, cantidad: Int) {
        val itemExistente = carritoDao.obtenerItemCarritoPorProductoId(productoId)
        if (itemExistente != null) {
            val itemActualizado = itemExistente.copy(cantidad = itemExistente.cantidad + cantidad)
            carritoDao.actualizarItem(itemActualizado)
        } else {
            val nuevoItem = CarritoEntity(productoId = productoId, cantidad = cantidad)
            carritoDao.insertarItem(nuevoItem)
        }
    }

    suspend fun eliminarItem(productoId: Int) {
        carritoDao.eliminarItemPorId(productoId)
    }

    suspend fun limpiarCarrito() {
        carritoDao.limpiarCarrito()
    }
}