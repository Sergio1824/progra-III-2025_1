package com.example.cookeasy.dataClasses

data class Categoria(
    val numCategoria: Int,
    val nombre:String,
    val imagenCategoria: String,     // depende de nosotros si usamos la imagen en url o lo metemos al drawable
    var cantidadRecetas: Int
)