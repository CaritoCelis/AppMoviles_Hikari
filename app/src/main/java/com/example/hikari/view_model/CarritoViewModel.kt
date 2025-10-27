package com.example.hikari.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hikari.modelo.DetallesItemCarrito
import com.example.hikari.repositorio.RepositorioCarrito
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CarritoViewModel(private val repositorioCarrito: RepositorioCarrito) : ViewModel() {
    val itemsCarritoConDetalles: StateFlow<List<DetallesItemCarrito>> = repositorioCarrito.itemsCarritoConDetalles
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun agregarAlCarrito(productoId: Int, cantidad: Int) {
        viewModelScope.launch {
            repositorioCarrito.agregarAlCarrito(productoId, cantidad)
        }
    }

    fun eliminarItem(productoId: Int) {
        viewModelScope.launch {
            repositorioCarrito.eliminarItem(productoId)
        }
    }

    fun limpiarCarrito() {
        viewModelScope.launch {
            repositorioCarrito.limpiarCarrito()
        }
    }
}