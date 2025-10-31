package com.example.cookeasy.dataClasses

import kotlinx.serialization.Serializable

@Serializable
data class Receta(
    val NumReceta: String,
    val titulo: String,
    val categoria: String,
    val imagenReceta: String,
    val dificultad: String,
    val tiempoPreparacion: String,
    val ingredientes: List<Ingrediente>,
    val instrucciones: List<Instruccion>,
    var esFavorito: Boolean
)