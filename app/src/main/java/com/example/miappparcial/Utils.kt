package com.example.miappparcial

import java.text.NumberFormat
import java.util.Locale

object Utils {

    private val formatoMoneda: NumberFormat by lazy {
        NumberFormat.getCurrencyInstance(Locale.US).apply {
            maximumFractionDigits = 0
        }
    }

    fun formatearMoneda(valor: Double): String = formatoMoneda.format(valor)

    fun parseDoubleOrNull(texto: String?): Double? =
        texto?.trim()?.replace(",", "")?.toDoubleOrNull()

    fun parseIntOrNull(texto: String?): Int? =
        texto?.trim()?.toIntOrNull()
}