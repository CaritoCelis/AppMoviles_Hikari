package com.example.hikari.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = HikariPink,
    secondary = HikariLavender,
    tertiary = HikariPeach,
    background = Color(0xFF1A1A1A),
    surface = Color(0xFF2D2D2D),
    surfaceVariant = HikariPinkDark.copy(alpha = 0.2f),
    onPrimary = HikariWhite,
    onSecondary = HikariOnSurface,
    onBackground = HikariWhite,
    onSurface = HikariWhite,
    primaryContainer = HikariPinkVariant,
    onPrimaryContainer = HikariWhite
)

private val LightColorScheme = lightColorScheme(
    primary = HikariPink,
    secondary = HikariLavender,
    tertiary = HikariPeach,
    background = HikariWhite,
    surface = HikariLightPink,
    surfaceVariant = HikariCream,
    onPrimary = HikariWhite,
    onSecondary = HikariOnSurface,
    onBackground = HikariOnSurface,
    onSurface = HikariOnSurface,
    primaryContainer = HikariPinkVariant,
    onPrimaryContainer = HikariWhite,
    secondaryContainer = HikariLavender,
    tertiaryContainer = HikariMint,
    outline = HikariPink.copy(alpha = 0.5f)
)

@Composable
fun PasteleriaHikariTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}