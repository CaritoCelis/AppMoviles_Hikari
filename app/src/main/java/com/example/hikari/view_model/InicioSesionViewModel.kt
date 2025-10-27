package com.example.hikari.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hikari.modelo.EstadoIUInicioSesion
import com.example.hikari.repositorio.RepositorioUsuario
import com.example.hikari.util.validarEmail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class InicioSesionViewModel (
    private val repositorioUsuario: RepositorioUsuario
) : ViewModel() {
    private val _estado = MutableStateFlow(EstadoIUInicioSesion())

    val estado = _estado

    fun alCambiarEmail(email: String) {


        _estado.update {
            it.copy(
                email = email,
                errores = it.errores.copy(errorEmail = validarEmail(email))
            )
        }
    }

    fun alCambiarContrasena(contrasena: String) {
        _estado.update {
            it.copy(
                contrasena = contrasena,
            )
        }
    }

    fun alEnviarInicioSesion(alExito: () -> Unit, alError: (String) -> Unit) {
        val estadoActual = estado.value

        if (estadoActual.errores.tieneErrores() || estadoActual.email.isEmpty() || estadoActual.contrasena.isEmpty()) {
            alError("Corrija todos los errores antes de envíar.")
            return
        }

        viewModelScope.launch {
            try {
                val usuario = repositorioUsuario.obtenerUsuarioPorEmail(estadoActual.email)
                if (usuario != null && usuario.contrasena == estadoActual.contrasena) {
                    alExito()
                } else {
                    alError("Correo electrónico o contraseña no válidos")
                }
            } catch (e: Exception) {
                alError("Error de inicio de sesión: ${e.message}")
            }
        }
    }
}