package com.example.alcoolougasolinaca.data

data class Posto(
    val nome: String,
    val coordenadas: Coordenadas,
    val alcool: Float,
    val gasolina: Float,
    val dataCadastro: String
)
{
}