package com.example.alcoolougasolinaca

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.alcoolougasolinaca.ui.theme.AlcoolOuGasolinaCaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AlcoolOuGasolinaCaTheme {
                Calcular()
            }
        }
    }
}


@Composable
fun Calcular() {
    var alcool by remember { mutableStateOf("") }
    var gasolina by remember { mutableStateOf("") }
    var nomePosto by remember {mutableStateOf("") }
    var switchLigado by remember { mutableStateOf(true) }
    var resultado by remember { mutableStateOf("") }
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
        Text(
            text = "75%"
        )
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
            if(precoAlcool != null && precoGasosa != null && precoGasosa!= 0f) {
                if ((precoAlcool / precoGasosa) < fator) {
                    resultado = "Melhor usar Alcool"
                } else {
                    resultado = "Melhor usar Gasolina"
                }
            }
        })
        {
            Text("Calcular")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = resultado
        )
        if(switchLigado){
            Text("Utilizando a metrica 75%")
        }
    }
}


@Preview(showBackground = true)
@Composable
fun TelaComSwitchPreview() {
    AlcoolOuGasolinaCaTheme {
        Calcular()
    }
}
