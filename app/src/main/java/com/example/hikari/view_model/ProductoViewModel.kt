package com.example.hikari.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hikari.modelo.ProductoEntity
import com.example.hikari.repositorio.RepositorioProducto
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class ProductoViewModel(private val repositorioProducto: RepositorioProducto) : ViewModel() {
    val productos: StateFlow<List<ProductoEntity>> = repositorioProducto.todosLosProductos
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
}