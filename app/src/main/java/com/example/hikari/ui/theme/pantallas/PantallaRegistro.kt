package com.example.hikari.ui.theme.pantallas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
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
        topBar = { BarraAplicacionHikari(titulo = "Registro", puedeNavegarAtras = true, alNavegarAtras = alNavegarAtras) },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Crear una Cuenta",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 32.dp),
            )

            OutlinedTextField(
                value = estado.email,
                onValueChange = viewModel::alCambiarEmail,
                label = { Text("Correo Electrónico") },
                isError = estado.errores.errorEmail != null,
                supportingText = { if (estado.errores.errorEmail != null) Text(estado.errores.errorEmail!!) },
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
            )

            OutlinedTextField(
                value = estado.contrasena,
                onValueChange = viewModel::alCambiarContrasena,
                label = { Text("Contraseña") },
                isError = estado.errores.errorContrasena != null,
                supportingText = { if (estado.errores.errorContrasena != null) Text(estado.errores.errorContrasena!!) },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
            )

            OutlinedTextField(
                value = estado.confirmarContrasena,
                onValueChange = viewModel::alCambiarConfirmarContrasena,
                label = { Text("Confirmar Contraseña") },
                isError = estado.errores.errorConfirmarContrasena != null,
                supportingText = { if (estado.errores.errorConfirmarContrasena != null) Text(estado.errores.errorConfirmarContrasena!!) },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp)
            )

            Button(
                onClick = {
                    viewModel.alEnviarRegistro(
                        alExito = {
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    message = "¡Registro exitoso! Por favor, inicie sesión.",
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
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Registrarse")
            }
        }
    }
}