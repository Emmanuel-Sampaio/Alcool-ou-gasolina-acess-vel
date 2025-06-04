package com.example.alcoolougasolinaca.view

import android.content.Context
import android.content.Intent

import android.net.Uri
import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.alcoolougasolinaca.data.Coordenadas
import com.example.alcoolougasolinaca.data.Posto
import org.json.JSONObject

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun ListaPostos(navController: NavHostController) {
    val context = LocalContext.current
    val listaDePostos = remember { mutableStateListOf<Posto>() }


    LaunchedEffect(Unit) {
        val postosSalvos = getListaPostosJSON(context)
        listaDePostos.clear()
        listaDePostos.addAll(postosSalvos)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Lista de Postos") },
                navigationIcon = {
                    Button(onClick = {
                        navController.navigate("calcular")
                    }) {
                        Text("< VOLTAR")
                    }
                }
            )
        }
    )
    { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(listaDePostos) { posto ->
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
                        text = posto.nome +
                        " \nValor do alcool " + posto.alcool +
                        " \nValor da gasolina " + posto.gasolina +
                        " \nCoordenadas " + posto.coordenadas.latitude + " " + posto.coordenadas.longitude +
                        " \nData de cadastro: " + posto.dataCadastro,
                        modifier = Modifier.padding(16.dp)
                    )
                    Row (
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){
                        Button(onClick = {

                            navController.navigate("EditarPosto/${posto.nome}")
                        }) {
                            Text("Editar")
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Button(onClick = {
                            listaDePostos.remove(posto)
                            saveListaPostosJSON(context, listaDePostos)

                        }, colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)) {
                            Text("Excluir")
                        }
                    }
                }
            }
        }
    }
}

fun postoToJson(posto: Posto): JSONObject {
    val json = JSONObject()
    json.put("nome", posto.nome)

    val coordenadasJson = JSONObject()
    coordenadasJson.put("latitude", posto.coordenadas.latitude)
    coordenadasJson.put("longitude", posto.coordenadas.longitude)
    json.put("coordenadas", coordenadasJson)
    json.put("alcool", posto.alcool)
    json.put("gasolina", posto.gasolina)
    json.put("dataCadastro", posto.dataCadastro)
    return json
}

fun jsonToPosto(json: JSONObject): Posto {
    val nome = json.getString("nome")
    val alcool = json.getDouble("alcool").toFloat()
    val gasolina = json.getDouble("gasolina").toFloat()

    val coordenadasJson = json.getJSONObject("coordenadas")
    val latitude = coordenadasJson.getDouble("latitude")
    val longitude = coordenadasJson.getDouble("longitude")
    val dataCadastro = json.getString("dataCadastro")
    return Posto(nome, Coordenadas(latitude, longitude), alcool, gasolina, dataCadastro )
}

fun saveListaPostosJSON(context: Context, listaPostos: List<Posto>) {
    val sharedFileName = "postoShared"
    val sp = context.getSharedPreferences(sharedFileName, Context.MODE_PRIVATE)
    val editor = sp.edit()

    val jsonArray = org.json.JSONArray()
    listaPostos.forEach { posto ->
        jsonArray.put(postoToJson(posto))
    }

    editor.putString("listaPostosJSON", jsonArray.toString())
    editor.apply()
}


fun getListaPostosJSON(context: Context): List<Posto> {
    val sharedFileName = "postoShared"
    val sp = context.getSharedPreferences(sharedFileName, Context.MODE_PRIVATE)
    val jsonString = sp.getString("listaPostosJSON", null) ?: return emptyList()

    val jsonArray = org.json.JSONArray(jsonString)
    val lista = mutableListOf<Posto>()
    for (i in 0 until jsonArray.length()) {
        val json = jsonArray.getJSONObject(i)
        lista.add(jsonToPosto(json))
    }
    return lista
}



