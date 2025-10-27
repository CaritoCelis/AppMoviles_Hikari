package com.example.hikari.ui.theme.pantallas

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.hikari.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarraAplicacionHikari(titulo: String, puedeNavegarAtras: Boolean, alNavegarAtras: () -> Unit) {
    CenterAlignedTopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(painter = painterResource(id = R.drawable.logo), contentDescription = "Logo Hikari", modifier = Modifier.size(32.dp))
                Spacer(modifier = Modifier.padding(start = 8.dp))
                Text(text = titulo, fontWeight = FontWeight.Bold)
            }
        },
        navigationIcon = {
            if (puedeNavegarAtras) {
                IconButton(onClick = alNavegarAtras) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Atrás")
                }
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.Transparent
        )
    )
}