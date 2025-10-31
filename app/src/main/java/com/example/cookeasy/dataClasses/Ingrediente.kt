package com.example.cookeasy.dataClasses

import kotlinx.serialization.Serializable


@Serializable
data class Ingrediente(
    val nombre: String,
    val cantidad: String   //no es int porq diremos cada ingrediente como: 2 cucharadas de :, 100 gramos de, etc.
)