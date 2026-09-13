package com.example.miappparcial

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun DescuentosScreen() {
    var precioTexto by remember { mutableStateOf("") }
    var descuentoTexto by remember { mutableStateOf("") }
    var errorPrecio by remember { mutableStateOf<String?>(null) }
    var errorDescuento by remember { mutableStateOf<String?>(null) }
    var resultado by remember { mutableStateOf<Pair<Double, Double>?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {
        Text("Calculadora de descuentos", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = precioTexto,
            onValueChange = { precioTexto = it },
            label = { Text("Precio original ($)") },
            isError = errorPrecio != null,
            supportingText = { errorPrecio?.let { Text(it) } },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = descuentoTexto,
            onValueChange = { descuentoTexto = it },
            label = { Text("Porcentaje de descuento (%)") },
            isError = errorDescuento != null,
            supportingText = { errorDescuento?.let { Text(it) } },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(20.dp))

        Button(
            onClick = {
                val precio = Utils.parseDoubleOrNull(precioTexto)
                val descuento = Utils.parseDoubleOrNull(descuentoTexto)

                errorPrecio = if (precio == null || precio <= 0.0)
                    "Ingresa un precio válido mayor que cero" else null
                errorDescuento = if (descuento == null || descuento < 0.0 || descuento > 100.0)
                    "El descuento debe estar entre 0% y 100%" else null

                resultado = if (errorPrecio == null && errorDescuento == null && precio != null && descuento != null) {
                    val valorDescuento = precio * (descuento / 100.0)
                    val precioFinal = precio - valorDescuento
                    valorDescuento to precioFinal
                } else null
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Calcular")
        }

        resultado?.let { (valorDescuento, precioFinal) ->
            Spacer(Modifier.height(20.dp))
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp)) {
                    Text("Descuento aplicado: ${Utils.formatearMoneda(valorDescuento)}")
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "Total a pagar: ${Utils.formatearMoneda(precioFinal)}",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }
}
