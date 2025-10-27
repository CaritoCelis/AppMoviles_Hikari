package com.example.hikari.ui.theme.pantallas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hikari.ui.theme.*
import com.example.hikari.view_model.InicioSesionViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaInicioSesion(
    alNavegarAInicio: () -> Unit = {},
    alNavegarARegistro: () -> Unit = {},
    alNavegarAtras: () -> Unit = {},
    viewModel: InicioSesionViewModel = viewModel(),
) {
    val estado by viewModel.estado.collectAsState()
    val estadoAnfitrionSnackbar = remember { SnackbarHostState() }
    val alcance = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(estadoAnfitrionSnackbar) },
        containerColor = HikariWhite
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(HikariLightPink, HikariWhite, HikariCream)
                    )
                )
                .padding(paddingValues)
        ) {
            // Botón de atrás decorado
            IconButton(
                onClick = alNavegarAtras,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(8.dp)
                    .size(48.dp)
                    .background(HikariWhite, CircleShape)
                    .shadow(4.dp, CircleShape)
            ) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = "Atrás",
                    tint = HikariPink
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Header decorativo
                Surface(
                    modifier = Modifier
                        .size(100.dp)
                        .padding(bottom = 24.dp),
                    shape = CircleShape,
                    color = HikariPink,
                    shadowElevation = 8.dp
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = "光",
                            fontSize = 48.sp,
                            fontWeight = FontWeight.Bold,
                            color = HikariWhite
                        )
                    }
                }

                Text(
                    text = "Bienvenido a",
                    fontSize = 20.sp,
                    color = HikariOnSurfaceLight,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "Pastelería Hikari",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = HikariPink,
                    modifier = Modifier.padding(bottom = 8.dp),
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "Inicia sesión para continuar",
                    fontSize = 16.sp,
                    color = HikariOnSurfaceLight,
                    modifier = Modifier.padding(bottom = 32.dp),
                    textAlign = TextAlign.Center
                )

                // Card contenedor de formulario
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = HikariWhite),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Campo de email decorado
                        OutlinedTextField(
                            value = estado.email,
                            onValueChange = viewModel::alCambiarEmail,
                            supportingText = {
                                if (estado.errores.errorEmail != null) {
                                    Text(text = estado.errores.errorEmail!!)
                                }
                            },
                            label = { Text("Correo Electrónico") },
                            leadingIcon = {
                                Icon(Icons.Default.Email, contentDescription = null, tint = HikariPink)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = HikariPink,
                                unfocusedBorderColor = HikariPink.copy(alpha = 0.3f),
                                focusedLabelColor = HikariPink,
                                cursorColor = HikariPink
                            )
                        )

                        // Campo de contraseña decorado
                        OutlinedTextField(
                            value = estado.contrasena,
                            onValueChange = viewModel::alCambiarContrasena,
                            supportingText = {
                                if (estado.errores.errorContrasena != null) {
                                    Text(text = estado.errores.errorContrasena!!)
                                }
                            },
                            label = { Text("Contraseña") },
                            leadingIcon = {
                                Icon(Icons.Default.Lock, contentDescription = null, tint = HikariPink)
                            },
                            visualTransformation = PasswordVisualTransformation(),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 24.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = HikariPink,
                                unfocusedBorderColor = HikariPink.copy(alpha = 0.3f),
                                focusedLabelColor = HikariPink,
                                cursorColor = HikariPink
                            )
                        )

                        // Botón de inicio de sesión decorado
                        Button(
                            onClick = {
                                viewModel.alEnviarInicioSesion(
                                    alExito = alNavegarAInicio,
                                    alError = { mensajeError ->
                                        alcance.launch {
                                            estadoAnfitrionSnackbar.showSnackbar(
                                                message = mensajeError,
                                                duration = SnackbarDuration.Short
                                            )
                                        }
                                    }
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .shadow(8.dp, RoundedCornerShape(16.dp)),
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = HikariPink
                            )
                        ) {
                            Text(
                                text = "Iniciar Sesión",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                // Link de registro decorado
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    color = HikariLavender.copy(alpha = 0.3f)
                ) {
                    TextButton(
                        onClick = alNavegarARegistro,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        Text(
                            text = "¿No tienes cuenta? ",
                            color = HikariOnSurfaceLight
                        )
                        Text(
                            text = "Regístrate aquí",
                            color = HikariPink,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}