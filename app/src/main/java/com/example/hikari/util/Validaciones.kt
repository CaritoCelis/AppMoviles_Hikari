package com.example.hikari.util

import android.util.Patterns

fun validarEmail(email: String): String? {
    if (email.isEmpty()) {
        return "El correo electrónico no puede estar vacío."
    }
    if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
        return "Formato de correo electrónico no válido."
    }
    return null
}
