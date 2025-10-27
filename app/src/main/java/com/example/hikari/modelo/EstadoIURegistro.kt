package com.example.hikari.modelo

import com.example.hikari.util.formularios.ErroresFormularioRegistro

data class EstadoIURegistro(
    val email: String = "",
    val contrasena: String = "",
    val confirmarContrasena: String = "",
    val errores: ErroresFormularioRegistro = ErroresFormularioRegistro()
)