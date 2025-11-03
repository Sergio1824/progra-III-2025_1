package com.example.cookeasy.dataClasses

import kotlinx.serialization.Serializable

//  Es una propiedad de la data class principal "Receta" para definir instrucciones

@Serializable
data class Instruccion(
    val numPaso: Int,
    val descripcion: String
)