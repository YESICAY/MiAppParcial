package com.example.miappparcial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.miappparcial.ui.theme.MiappParcialTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MiappParcialTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    var nombreRegistrado by remember { mutableStateOf<String?>(null) }

                    Box(modifier = Modifier.padding(innerPadding)) {
                        if (nombreRegistrado == null) {
                            RegistrationScreen(onRegistrado = { nombre -> nombreRegistrado = nombre })
                        } else {
                            MainTabsScreen(nombre = nombreRegistrado!!)
                        }
                    }
                }
            }
        }
    }
}