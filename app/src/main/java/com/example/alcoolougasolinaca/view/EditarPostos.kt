package com.example.alcoolougasolinaca.view


import androidx.compose.foundation.layout.*

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.alcoolougasolinaca.R


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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(stringResource(R.string.editar) + " ${posto.nome}")
            Row (verticalAlignment = Alignment.CenterVertically)
            {
                TextField(
                    value = alcool,
                    onValueChange = { alcool = it },
                    label = { Text(stringResource(R.string.preco_alcool)) },
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.padding(10.dp))
                Button( onClick = {
                    alcool = "0.0";
                },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)

                )

                {

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(stringResource(R.string.excluir))
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            painter = painterResource(id = R.drawable.delete_icon),
                            contentDescription = stringResource(R.string.excluir_icon),
                            modifier = Modifier.size(20.dp)
                        )

                    }
                }
            }


            Spacer(modifier = Modifier.height(8.dp))
            Row  (verticalAlignment = Alignment.CenterVertically) {
                TextField(
                    value = gasolina,
                    onValueChange = { gasolina = it },
                    label = { Text(stringResource(R.string.preco_gasolina)) },
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.padding(10.dp))
                Button( onClick = {
                    gasolina = "0.0";
                },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)

                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(stringResource(R.string.excluir))
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            painter = painterResource(id = R.drawable.delete_icon),
                            contentDescription = stringResource(R.string.excluir_icon),
                            modifier = Modifier.size(20.dp)
                        )

                    }
                }
            }


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

                Text(stringResource(R.string.salvar_edicao))
            }
        }
    }
}
