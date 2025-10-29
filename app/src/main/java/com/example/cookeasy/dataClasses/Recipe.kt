package com.example.cookeasy.dataClasses

    data class Recipe(
        val title: String,
        val description: String,
        val imageResId: Int,
        val ingredients: String,
        val steps: String
    )