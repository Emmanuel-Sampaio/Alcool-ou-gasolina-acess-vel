package com.example.alcoolougasolinaca.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.alcoolougasolinaca.data.Coordenadas
import com.example.alcoolougasolinaca.data.Posto


@Composable
fun Calcular(navController: NavHostController) {
    var alcool by rememberSaveable { mutableStateOf("") }
    var gasolina by rememberSaveable { mutableStateOf("") }
    var nomePosto by rememberSaveable { mutableStateOf("") }
    var switchLigado by rememberSaveable { mutableStateOf(true) }
    var resultado by rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(150.dp))

        TextField(
            value = alcool,
            onValueChange = { alcool = it },
            label = { Text("Preço do álcool (R$)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = gasolina,
            onValueChange = { gasolina = it },
            label = { Text("Preço da gasolina (R$)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = nomePosto,
            onValueChange = { nomePosto = it },
            label = { Text("Nome do Posto") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "75%")
            Spacer(modifier = Modifier.width(8.dp))
            Switch(
                checked = switchLigado,
                onCheckedChange = { switchLigado = it }
            )
        }

        Button(onClick = {
            val precoAlcool = alcool.toFloatOrNull()
            val precoGasosa = gasolina.toFloatOrNull()
            val fator = if (switchLigado) 0.75f else 0.7f
            if (precoAlcool != null && precoGasosa != null && precoGasosa != 0f) {
                resultado = if ((precoAlcool / precoGasosa) < fator) {
                    "Melhor usar Alcool"
                } else {
                    "Melhor usar Gasolina"
                }
            }
        }) {
            Text("Calcular")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = resultado)

        if (switchLigado) {
            Text("Utilizando a metrica 75%")
        }

        Spacer(modifier = Modifier.height(16.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Button(
                onClick = {
                    if (nomePosto.isNotBlank()) {
                        val precoAlcool = alcool.toFloatOrNull()
                        val precoGasosa = gasolina.toFloatOrNull()
                        val postosExistentes = getListaPostosJSON(context).toMutableList()
                        val novoPosto = Posto(
                            nomePosto,
                            coordenadas = Coordenadas(41.40338, 2.17403),
                            alcool = precoAlcool ?: 0f,
                            gasolina = precoGasosa ?: 0f,


                        )

                        postosExistentes.add(novoPosto)
                        saveListaPostosJSON(context, postosExistentes)

                        navController.navigate("ListaPostos")
                    }
                }
            ) {
                Text("Salvar Posto")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(
                onClick = {
                    navController.navigate("ListaPostos")
                }
            ) {
                Text("Veja os postos!")
            }
        }

    }
}


