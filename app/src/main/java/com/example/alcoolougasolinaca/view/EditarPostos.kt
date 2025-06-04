package com.example.alcoolougasolinaca.view


import androidx.compose.foundation.layout.*

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController


@Composable
fun EditarPosto(nomePosto: String, navController: NavHostController) {
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) {
        val context = LocalContext.current
        val lista = getListaPostosJSON(context).toMutableList()
        val posto = lista.find { it.nome == nomePosto } ?: return@Surface

        var alcool by rememberSaveable { mutableStateOf(posto.alcool.toString()) }
        var gasolina by rememberSaveable { mutableStateOf(posto.gasolina.toString()) }

        Column(modifier = Modifier.padding(24.dp)) {
            Text("Editando: ${posto.nome}")

            TextField(
                value = alcool,
                onValueChange = { alcool = it },
                label = { Text("Preço do Álcool") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = gasolina,
                onValueChange = { gasolina = it },
                label = { Text("Preço da Gasolina") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                val novoPosto = posto.copy(
                    alcool = alcool.toFloatOrNull() ?: 0f,
                    gasolina = gasolina.toFloatOrNull() ?: 0f
                )
                lista.removeIf { it.nome == posto.nome }
                lista.add(novoPosto)
                saveListaPostosJSON(context, lista)
                navController.navigate("ListaPostos")
            }) {
                Text("Salvar Alterações")
            }
        }
    }
}
