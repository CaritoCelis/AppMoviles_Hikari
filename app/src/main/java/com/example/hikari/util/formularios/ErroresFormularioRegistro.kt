package com.example.hikari.util.formularios

data class ErroresFormularioRegistro(
    val errorEmail: String? = null,
    val errorContrasena: String? = null,
    val errorConfirmarContrasena: String? = null
) {
    fun tieneErrores(): Boolean {
        return errorEmail != null || errorContrasena != null || errorConfirmarContrasena != null
    }
}
