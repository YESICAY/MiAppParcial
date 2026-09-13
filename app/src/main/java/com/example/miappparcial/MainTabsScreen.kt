package com.example.miappparcial

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.unit.dp

@Composable
fun MainTabsScreen(nombre: String) {
    var tabSeleccionada by remember { mutableIntStateOf(0) }
    val titulos = listOf("Descuentos", "Dividir cuenta", "Compra a cuotas")

    Column(Modifier) {
        Text(
            text = "Hola, $nombre",
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.titleMedium
        )

        TabRow(selectedTabIndex = tabSeleccionada) {
            titulos.forEachIndexed { index, titulo ->
                Tab(
                    selected = tabSeleccionada == index,
                    onClick = { tabSeleccionada = index },
                    text = { Text(titulo) }
                )
            }
        }

        when (tabSeleccionada) {
            0 -> DescuentosScreen()
            1 -> DividirCuentaScreen()
            else -> ComprasCuotasScreen()
        }
    }
}