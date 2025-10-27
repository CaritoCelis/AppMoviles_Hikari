package com.example.hikari.repositorio

import com.example.hikari.modelo.ProductoDao
import com.example.hikari.modelo.ProductoEntity
import kotlinx.coroutines.flow.Flow

class RepositorioProducto(private val productoDao: ProductoDao) {
    val todosLosProductos: Flow<List<ProductoEntity>> = productoDao.obtenerTodosLosProductos()

    suspend fun insertarTodos(productos: List<ProductoEntity>) {
        productoDao.insertarTodos(productos)
    }

    suspend fun contar(): Int {
        return productoDao.contar()
    }
}