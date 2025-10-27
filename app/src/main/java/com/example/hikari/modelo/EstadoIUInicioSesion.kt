package com.example.hikari.modelo

import com.example.hikari.util.formularios.ErroresFormularioInicioSesion

data class EstadoIUInicioSesion(
    val email: String = "",
    val contrasena: String = "",
    val errores: ErroresFormularioInicioSesion = ErroresFormularioInicioSesion()
)