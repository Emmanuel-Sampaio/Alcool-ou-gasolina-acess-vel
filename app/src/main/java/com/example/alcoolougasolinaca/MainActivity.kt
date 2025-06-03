package com.example.alcoolougasolinaca

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.alcoolougasolinaca.ui.theme.AlcoolOuGasolinaCaTheme
import com.example.alcoolougasolinaca.view.Calcular
import com.example.alcoolougasolinaca.view.EditarPosto
import com.example.alcoolougasolinaca.view.ListaPostos

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 1)
        }
        setContent {
            AlcoolOuGasolinaCaTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "calcular") {
                    composable("calcular") {
                        Calcular(navController)
                    }

                    composable("ListaPostos") {
                        ListaPostos(navController)
                    }
                    composable("EditarPosto/{nome}") { backStackEntry ->
                        val nome = backStackEntry.arguments?.getString("nome") ?: ""
                        EditarPosto(nome, navController)
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
