package com.example.alcoolougasolinaca.view

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.alcoolougasolinaca.data.Coordenadas
import com.example.alcoolougasolinaca.data.Posto

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaPostos(navController: NavHostController, posto: String) {
    val context = LocalContext.current


    val postos = listOf(
        Posto("Posto SP", Coordenadas(41.40338, 2.17403)),
        Posto("Posto NY", Coordenadas(40.7128, -74.0060)),
        Posto("Posto RJ", Coordenadas(-22.9068, -43.1729))
    )

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Lista de Postos") })
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(postos) { posto ->
                Card(
                    onClick = {

                        val gmmIntentUri = Uri.parse("geo:0,0?q=${posto.coordenadas.latitude},${posto.coordenadas.longitude}(${posto.nome})")
                        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri).apply {
                            setPackage("com.google.android.apps.maps")
                        }
                        context.startActivity(mapIntent)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Text(
                        text = posto.nome,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}
