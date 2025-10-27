package com.example.hikari.repositorio

import com.example.hikari.modelo.UsuarioDao
import com.example.hikari.modelo.UsuarioEntity

class RepositorioUsuario(private val usuarioDao: UsuarioDao) {

    suspend fun insertarUsuario(email: String, contrasena: String): Long {
        val usuario = UsuarioEntity(email = email, contrasena = contrasena)
        return usuarioDao.insertar(usuario)
    }

    suspend fun obtenerUsuarioPorEmail(email: String): UsuarioEntity? {
        return usuarioDao.buscarPorEmail(email)
    }

    suspend fun existeEmail(email: String): Boolean {
        return usuarioDao.buscarPorEmail(email) != null
    }
}