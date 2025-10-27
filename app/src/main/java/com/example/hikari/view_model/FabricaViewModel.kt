package com.example.hikari.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.hikari.repositorio.RepositorioCarrito
import com.example.hikari.repositorio.RepositorioProducto
import com.example.hikari.repositorio.RepositorioUsuario

class FabricaViewModel(
    private val repositorioUsuario: RepositorioUsuario,
    private val repositorioProducto: RepositorioProducto,
    private val repositorioCarrito: RepositorioCarrito
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(RegistroViewModel::class.java) -> {
                RegistroViewModel(repositorioUsuario) as T
            }
            modelClass.isAssignableFrom(InicioSesionViewModel::class.java) -> {
                InicioSesionViewModel(repositorioUsuario) as T
            }
            modelClass.isAssignableFrom(ProductoViewModel::class.java) -> {
                ProductoViewModel(repositorioProducto) as T
            }
            modelClass.isAssignableFrom(CarritoViewModel::class.java) -> {
                CarritoViewModel(repositorioCarrito) as T
            }
            else -> throw IllegalArgumentException("Clase de ViewModel desconocida: ${modelClass.name}")
        }
    }
}