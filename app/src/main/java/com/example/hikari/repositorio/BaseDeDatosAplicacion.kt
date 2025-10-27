package com.example.hikari.repositorio

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.hikari.modelo.*

@Database(entities = [UsuarioEntity::class, ProductoEntity::class, CarritoEntity::class], version = 7, exportSchema = false)
abstract class BaseDeDatosAplicacion : RoomDatabase() {
    abstract fun usuarioDao(): UsuarioDao
    abstract fun productoDao(): ProductoDao
    abstract fun carritoDao(): CarritoDao

    companion object {
        @Volatile
        private var INSTANCIA: BaseDeDatosAplicacion? = null

        fun obtenerBaseDeDatos(context: Context): BaseDeDatosAplicacion {
            return INSTANCIA ?: synchronized(this) {
                val instancia = Room.databaseBuilder(
                    context.applicationContext,
                    BaseDeDatosAplicacion::class.java,
                    "pasteleria_hikari_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCIA = instancia
                instancia
            }
        }
    }
}