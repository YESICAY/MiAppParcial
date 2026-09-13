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
fun DividirCuentaScreen() {
    var totalTexto by remember { mutableStateOf("") }
    var personasTexto by remember { mutableStateOf("") }
    var propinaTexto by remember { mutableStateOf("") }
    var errorTotal by remember { mutableStateOf<String?>(null) }
    var errorPersonas by remember { mutableStateOf<String?>(null) }
    var errorPropina by remember { mutableStateOf<String?>(null) }
    var resultado by remember { mutableStateOf<Triple<Double, Double, Double>?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {
        Text("Dividir cuenta", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = totalTexto,
            onValueChange = { totalTexto = it },
            label = { Text("Valor total de la cuenta ($)") },
            isError = errorTotal != null,
            supportingText = { errorTotal?.let { Text(it) } },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = personasTexto,
            onValueChange = { personasTexto = it },
            label = { Text("Número de personas") },
            isError = errorPersonas != null,
            supportingText = { errorPersonas?.let { Text(it) } },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = propinaTexto,
            onValueChange = { propinaTexto = it },
            label = { Text("Porcentaje de propina (%)") },
            isError = errorPropina != null,
            supportingText = { errorPropina?.let { Text(it) } },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(20.dp))

        Button(
            onClick = {
                val total = Utils.parseDoubleOrNull(totalTexto)
                val personas = Utils.parseIntOrNull(personasTexto)
                val propina = Utils.parseDoubleOrNull(propinaTexto)

                errorTotal = if (total == null || total <= 0.0) "Ingresa un valor de cuenta válido" else null
                errorPersonas = if (personas == null || personas <= 0) "El número de personas debe ser mayor que cero" else null
                errorPropina = if (propina == null || propina < 0.0) "La propina no puede ser negativa" else null

                resultado = if (errorTotal == null && errorPersonas == null && errorPropina == null &&
                    total != null && personas != null && propina != null
                ) {
                    val propinaValor = total * (propina / 100.0)
                    val totalConPropina = total + propinaValor
                    val porPersona = totalConPropina / personas
                    Triple(propinaValor, totalConPropina, porPersona)
                } else null
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Calcular")
        }

        resultado?.let { (propinaValor, totalConPropina, porPersona) ->
            Spacer(Modifier.height(20.dp))
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp)) {
                    Text("Propina: ${Utils.formatearMoneda(propinaValor)}")
                    Spacer(Modifier.height(8.dp))
                    Text("Total: ${Utils.formatearMoneda(totalConPropina)}")
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "Cada persona paga: ${Utils.formatearMoneda(porPersona)}",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }
}
