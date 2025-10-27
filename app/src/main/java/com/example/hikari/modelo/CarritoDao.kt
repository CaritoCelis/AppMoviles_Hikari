package com.example.hikari.modelo

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CarritoDao {
    @Query("""
        SELECT p.id as productoId, p.nombre, p.precio, c.cantidad
        FROM carrito c
        JOIN productos p ON c.productoId = p.id
    """)
    fun obtenerItemsCarritoConDetalles(): Flow<List<DetallesItemCarrito>>

    @Query("SELECT * FROM carrito WHERE productoId = :productoId")
    suspend fun obtenerItemCarritoPorProductoId(productoId: Int): CarritoEntity?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertarItem(item: CarritoEntity)

    @Update
    suspend fun actualizarItem(item: CarritoEntity)

    @Query("DELETE FROM carrito WHERE productoId = :productoId")
    suspend fun eliminarItemPorId(productoId: Int)

    @Query("DELETE FROM carrito")
    suspend fun limpiarCarrito()
}