package com.example.cookeasy.dataClasses

import kotlinx.serialization.Serializable

//  Es una propiedad de la data class principal "Receta" para definir ingredientes
@Serializable
data class Ingrediente(
    val nombre: String,
    val cantidad: String   //no es int porq diremos cada ingrediente como: 2 cucharadas de :, 100 gramos de, etc.
)