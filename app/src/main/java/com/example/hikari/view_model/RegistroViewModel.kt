package com.example.hikari.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hikari.modelo.EstadoIURegistro
import com.example.hikari.repositorio.RepositorioUsuario
import com.example.hikari.util.validarEmail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegistroViewModel(
    private val repositorioUsuario: RepositorioUsuario
) : ViewModel() {
    private val _estado = MutableStateFlow(EstadoIURegistro())
    val estado = _estado.asStateFlow()

    fun alCambiarEmail(email: String) {
        _estado.update { it.copy(
            email = email,
            errores = it.errores.copy(errorEmail = validarEmail(email))
        ) }
    }

    fun alCambiarContrasena(contrasena: String) {
        val errorContrasena = if (contrasena.length < 6) "La contraseña debe tener al menos 6 caracteres." else null
        _estado.update { it.copy(
            contrasena = contrasena,
            errores = it.errores.copy(errorContrasena = errorContrasena)
        ) }
    }

    fun alCambiarConfirmarContrasena(confirmarContrasena: String) {
        val errorConfirmarContrasena = if (confirmarContrasena != _estado.value.contrasena) "Las contraseñas no coinciden." else null
        _estado.update { it.copy(
            confirmarContrasena = confirmarContrasena,
            errores = it.errores.copy(errorConfirmarContrasena = errorConfirmarContrasena)
        ) }
    }

    fun alEnviarRegistro(alExito: () -> Unit, alError: (String) -> Unit) {
        val estadoActual = _estado.value
        if (estadoActual.errores.tieneErrores()) {
            alError("Por favor, corrija los errores.")
            return
        }

        viewModelScope.launch {
            if (repositorioUsuario.existeEmail(estadoActual.email)) {
                alError("Este correo ya está registrado.")
                return@launch
            }

            val idUsuario = repositorioUsuario.insertarUsuario(estadoActual.email, estadoActual.contrasena)
            if (idUsuario != -1L) {
                alExito()
            } else {
                alError("Ocurrió un error durante el registro.")
            }
        }
    }
}