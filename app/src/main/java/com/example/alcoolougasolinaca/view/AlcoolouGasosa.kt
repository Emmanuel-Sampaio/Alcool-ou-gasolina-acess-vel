package com.example.alcoolougasolinaca.view

import android.app.Activity
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.alcoolougasolinaca.R
import com.example.alcoolougasolinaca.data.Coordenadas
import com.example.alcoolougasolinaca.data.Posto
import com.google.android.gms.location.LocationServices
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun Calcular(navController: NavHostController) {
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) {
        var alcool by rememberSaveable { mutableStateOf("") }
        var gasolina by rememberSaveable { mutableStateOf("") }
        var nomePosto by rememberSaveable { mutableStateOf("") }
        var switchLigado by rememberSaveable { mutableStateOf(true) }
        var resultadoResId by rememberSaveable { mutableStateOf<Int?>(null) }

        val precoAlcool = alcool.toFloatOrNull()
        val precoGasosa = gasolina.toFloatOrNull()
        val fator = if (switchLigado) 0.75f else 0.7f

        if (precoAlcool != null && precoGasosa != null && precoGasosa != 0f) {
            resultadoResId = if ((precoAlcool / precoGasosa) < fator) {
                R.string.melhor_usar_alcool
            } else {
                R.string.melhor_usar_gasolina
            }
        }
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
                label = { Text(stringResource(R.string.preco_alcool)) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = gasolina,
                onValueChange = { gasolina = it },
                label = { Text(stringResource(R.string.preco_gasolina)) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = nomePosto,
                onValueChange = { nomePosto = it },
                label = { Text(stringResource(R.string.nome_posto)) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "75%")
                Spacer(modifier = Modifier.width(8.dp))
                Switch(
                    checked = switchLigado,
                    onCheckedChange = { switchLigado = it }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))



            Spacer(modifier = Modifier.height(16.dp))

            resultadoResId?.let {
                Text(stringResource(it))
            }

            if (switchLigado) {
                Text("75%") //
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Button(onClick = {
                    val fusedLocationClient =
                        LocationServices.getFusedLocationProviderClient(context as Activity)
                    fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                        if (nomePosto.isNotBlank() && location != null) {
                            val precoAlcool = alcool.toFloatOrNull()
                            val precoGasosa = gasolina.toFloatOrNull()
                            val postosExistentes = getListaPostosJSON(context).toMutableList()

                            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                            val dataAtual = sdf.format(Date())

                            val novoPosto = Posto(
                                nome = nomePosto,
                                coordenadas = Coordenadas(location.latitude, location.longitude),
                                alcool = precoAlcool ?: 0f,
                                gasolina = precoGasosa ?: 0f,
                                dataCadastro = dataAtual
                            )

                            postosExistentes.add(novoPosto)
                            saveListaPostosJSON(context, postosExistentes)

                            navController.navigate("ListaPostos")
                        }
                    }
                }) {
                    Text(stringResource(R.string.salvar_posto))
                }

                Spacer(modifier = Modifier.width(16.dp))

                Button(onClick = {
                    navController.navigate("ListaPostos")
                }) {
                    Text(stringResource(R.string.veja_postos))
                }
            }
        }
    }
}