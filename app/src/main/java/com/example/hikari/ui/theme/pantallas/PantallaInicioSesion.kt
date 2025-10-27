package com.example.hikari.ui.theme.pantallas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
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
        snackbarHost = { SnackbarHost(estadoAnfitrionSnackbar) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            IconButton(
                onClick = alNavegarAtras,
                modifier = Modifier
                    .align(Alignment.TopStart)
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Atrás")
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Iniciar Sesión",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 32.dp),
                )

                OutlinedTextField(
                    value = estado.email,
                    onValueChange = viewModel::alCambiarEmail,
                    supportingText = {
                        if (estado.errores.errorEmail != null) { Text(text = estado.errores.errorEmail!!) }
                    },
                    label = { Text("Correo Electrónico") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )

                OutlinedTextField(
                    value = estado.contrasena,
                    onValueChange = viewModel::alCambiarContrasena,
                    supportingText = {
                        if (estado.errores.errorContrasena != null) { Text(text = estado.errores.errorContrasena!!) }
                    },
                    label = { Text("Contraseña") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp)
                )

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
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Iniciar Sesión")
                }

                TextButton(
                    onClick = alNavegarARegistro,
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Text("¿No tienes una cuenta? Regístrate")
                }
            }
        }
    }
}