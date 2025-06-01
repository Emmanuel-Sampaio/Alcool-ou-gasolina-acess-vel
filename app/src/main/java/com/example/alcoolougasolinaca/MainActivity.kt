package com.example.alcoolougasolinaca

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.alcoolougasolinaca.ui.theme.AlcoolOuGasolinaCaTheme
import com.example.alcoolougasolinaca.view.Calcular
import com.example.alcoolougasolinaca.view.ListaPostos

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AlcoolOuGasolinaCaTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "calcular") {
                    composable("calcular") { Calcular(navController) }

                    composable("ListaPostos") {
                        ListaPostos(navController)
                    }
                }
            }
        }
    }
}








@Preview(showBackground = true)
@Composable
fun TelaComSwitchPreview() {
    AlcoolOuGasolinaCaTheme {

    }
}
