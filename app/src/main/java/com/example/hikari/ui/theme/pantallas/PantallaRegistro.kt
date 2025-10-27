package com.example.hikari.ui.theme.pantallas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.example.hikari.view_model.RegistroViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaRegistro(
    alNavegarAInicioSesion: () -> Unit,
    alNavegarAtras: () -> Unit,
    viewModel: RegistroViewModel = viewModel()
) {
    val estado by viewModel.estado.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            BarraAplicacionHikari(
                titulo = "Registro",
                puedeNavegarAtras = true,
                alNavegarAtras = alNavegarAtras
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = HikariWhite
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(HikariCream, HikariWhite, HikariLightPink)
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Header decorativo
                Text(
                    text = "Únete a",
                    fontSize = 20.sp,
                    color = HikariOnSurfaceLight,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 24.dp)
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
                    text = "Crea tu cuenta y disfruta",
                    fontSize = 16.sp,
                    color = HikariOnSurfaceLight,
                    modifier = Modifier.padding(bottom = 32.dp),
                    textAlign = TextAlign.Center
                )

                // Card contenedor del formulario
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
                        // Campo de email
                        OutlinedTextField(
                            value = estado.email,
                            onValueChange = viewModel::alCambiarEmail,
                            label = { Text("Correo Electrónico") },
                            leadingIcon = {
                                Icon(Icons.Default.Email, contentDescription = null, tint = HikariPink)
                            },
                            isError = estado.errores.errorEmail != null,
                            supportingText = {
                                if (estado.errores.errorEmail != null) Text(estado.errores.errorEmail!!)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = HikariPink,
                                unfocusedBorderColor = HikariPink.copy(alpha = 0.3f),
                                focusedLabelColor = HikariPink,
                                cursorColor = HikariPink,
                                errorBorderColor = HikariError
                            )
                        )

                        // Campo de contraseña
                        OutlinedTextField(
                            value = estado.contrasena,
                            onValueChange = viewModel::alCambiarContrasena,
                            label = { Text("Contraseña") },
                            leadingIcon = {
                                Icon(Icons.Default.Lock, contentDescription = null, tint = HikariPink)
                            },
                            isError = estado.errores.errorContrasena != null,
                            supportingText = {
                                if (estado.errores.errorContrasena != null) Text(estado.errores.errorContrasena!!)
                            },
                            visualTransformation = PasswordVisualTransformation(),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = HikariPink,
                                unfocusedBorderColor = HikariPink.copy(alpha = 0.3f),
                                focusedLabelColor = HikariPink,
                                cursorColor = HikariPink,
                                errorBorderColor = HikariError
                            )
                        )

                        // Campo de confirmar contraseña
                        OutlinedTextField(
                            value = estado.confirmarContrasena,
                            onValueChange = viewModel::alCambiarConfirmarContrasena,
                            label = { Text("Confirmar Contraseña") },
                            leadingIcon = {
                                Icon(Icons.Default.Lock, contentDescription = null, tint = HikariLavender)
                            },
                            isError = estado.errores.errorConfirmarContrasena != null,
                            supportingText = {
                                if (estado.errores.errorConfirmarContrasena != null) Text(estado.errores.errorConfirmarContrasena!!)
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
                                cursorColor = HikariPink,
                                errorBorderColor = HikariError
                            )
                        )

                        // Botón de registro
                        Button(
                            onClick = {
                                viewModel.alEnviarRegistro(
                                    alExito = {
                                        scope.launch {
                                            snackbarHostState.showSnackbar(
                                                message = "✨ ¡Registro exitoso! Inicia sesión.",
                                                duration = SnackbarDuration.Short
                                            )
                                            alNavegarAInicioSesion()
                                        }
                                    },
                                    alError = { mensajeError ->
                                        scope.launch {
                                            snackbarHostState.showSnackbar(
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
                                text = "Crear Cuenta",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}