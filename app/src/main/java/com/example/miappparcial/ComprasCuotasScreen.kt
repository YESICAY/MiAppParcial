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
import kotlin.math.pow

@Composable
fun ComprasCuotasScreen() {
    var valorTexto by remember { mutableStateOf("") }
    var cuotasTexto by remember { mutableStateOf("") }
    var tasaTexto by remember { mutableStateOf("") }
    var errorValor by remember { mutableStateOf<String?>(null) }
    var errorCuotas by remember { mutableStateOf<String?>(null) }
    var errorTasa by remember { mutableStateOf<String?>(null) }
    var resultado by remember { mutableStateOf<List<Double>?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {
        Text("Compra a cuotas", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = valorTexto,
            onValueChange = { valorTexto = it },
            label = { Text("Valor del producto ($)") },
            isError = errorValor != null,
            supportingText = { errorValor?.let { Text(it) } },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = cuotasTexto,
            onValueChange = { cuotasTexto = it },
            label = { Text("Número de cuotas") },
            isError = errorCuotas != null,
            supportingText = { errorCuotas?.let { Text(it) } },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = tasaTexto,
            onValueChange = { tasaTexto = it },
            label = { Text("Tasa de interés mensual (%)") },
            isError = errorTasa != null,
            supportingText = { errorTasa?.let { Text(it) } },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(20.dp))

        Button(
            onClick = {
                val valor = Utils.parseDoubleOrNull(valorTexto)
                val cuotas = Utils.parseIntOrNull(cuotasTexto)
                val tasa = Utils.parseDoubleOrNull(tasaTexto)

                errorValor = if (valor == null || valor <= 0.0) "El valor del producto debe ser mayor que cero" else null
                errorCuotas = if (cuotas == null || cuotas <= 0) "El número de cuotas debe ser mayor que cero" else null
                errorTasa = if (tasa == null || tasa < 0.0) "La tasa de interés no puede ser negativa" else null

                resultado = if (errorValor == null && errorCuotas == null && errorTasa == null &&
                    valor != null && cuotas != null && tasa != null
                ) {
                    val valorCuota = if (tasa == 0.0) {
                        valor / cuotas
                    } else {
                        val i = tasa / 100.0
                        val factor = 1.0 - (1.0 + i).pow(-cuotas)
                        (valor * i) / factor
                    }
                    val totalPagar = valorCuota * cuotas
                    val interesTotal = totalPagar - valor
                    listOf(valor, valorCuota, totalPagar, interesTotal)
                } else null
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Calcular")
        }

        resultado?.let { (valorOriginal, valorCuota, totalPagar, interesTotal) ->
            Spacer(Modifier.height(20.dp))
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp)) {
                    Text(
                        "Valor original: ${Utils.formatearMoneda(valorOriginal)}")
                    Spacer(Modifier.height(8.dp))
                    Text("Valor de cada cuota: ${Utils.formatearMoneda(valorCuota)}",
                        style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.height(8.dp))
                    Text("Total a pagar: ${Utils.formatearMoneda(totalPagar)}")
                    Spacer(Modifier.height(8.dp))
                    Text("Interés total: ${Utils.formatearMoneda(interesTotal)}")
                }
            }
        }
    }
}