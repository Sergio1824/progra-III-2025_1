package com.example.cookeasy.dataClasses

import kotlinx.serialization.Serializable


@Serializable
data class Instruccion(
    val numPaso: Int,
    val descripcion: String
)