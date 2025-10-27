package com.example.hikari.util.formularios

data class ErroresFormularioInicioSesion(
    val errorEmail: String? = null,
    val errorContrasena: String? = null
) {
    fun tieneErrores(): Boolean {
        return errorEmail != null || errorContrasena != null
    }
}
