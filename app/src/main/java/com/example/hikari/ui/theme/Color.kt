package com.example.hikari.ui.theme

import androidx.compose.ui.graphics.Color

// Paleta de Colores Pastel "Hikari" - Inspirada en pastelerías japonesas
// Colores principales - Rosa Sakura
val HikariPink = Color(0xFFFFB3D9) // Rosa pastel suave (principal)
val HikariPinkVariant = Color(0xFFFF87B2) // Rosa pastel más vibrante
val HikariPinkDark = Color(0xFFD81B60) // Rosa fuerte para acentos importantes

// Colores complementarios pastel
val HikariLavender = Color(0xFFE6D5F5) // Lavanda pastel
val HikariPeach = Color(0xFFFFDDB3) // Durazno pastel
val HikariMint = Color(0xFFD5F5E3) // Menta pastel
val HikariCream = Color(0xFFFFF9E6) // Crema/vainilla
val HikariLightPink = Color(0xFFFFF0F5) // Rosa muy pálido para fondos

// Colores de acento
val HikariGold = Color(0xFFFFD700) // Dorado suave para detalles premium
val HikariLightGold = Color(0xFFFFF8DC) // Dorado muy claro

// Colores para texto y superficie
val HikariOnSurface = Color(0xFF1C1B1F) // Texto oscuro
val HikariOnSurfaceLight = Color(0xFF6B6B6B) // Texto secundario
val HikariWhite = Color(0xFFFFFBFF) // Blanco crema

// Colores para estados
val HikariSuccess = Color(0xFFC8E6C9) // Verde pastel para éxito
val HikariWarning = Color(0xFFFFE0B2) // Naranja pastel para advertencia
val HikariError = Color(0xFFFFCDD2) // Rojo pastel para error

// Gradientes (para usar con Brush.horizontalGradient o verticalGradient)
val HikariGradientPink = listOf(HikariLightPink, HikariPink)
val HikariGradientSunset = listOf(HikariPeach, HikariPink, HikariLavender)
val HikariGradientDream = listOf(HikariCream, HikariLightPink, HikariMint)