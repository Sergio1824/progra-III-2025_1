package com.example.cookeasy.managers

import android.content.Context


object FavoritesManager {

    fun isFavorite(context: Context, recipeId: String): Boolean {
        // busca en la lista de recetas si la receta es favorita
        val recipe = RecipeManager.getRecipes(context).find { it.NumReceta == recipeId }
        return recipe?.esFavorito ?: false
    }   // le pide al RecipeManager la lista completa de recetas, busca la receta con su Id
        // y devuelve el valor que tenga en el boolean de Receta

    fun toggleFavorite(context: Context, recipeId: String) {
        // obtiene la lista actual de recetas
        val allRecipes = RecipeManager.getRecipes(context)

        // busca la receta que queremos cambiar
        val recipeToUpdate = allRecipes.find { it.NumReceta == recipeId }

        // si la encuentra, cambia su estado: esFavorito
        recipeToUpdate?.let {
            it.esFavorito = !it.esFavorito
        }
        // guarda la lista de nuevo con el cambio
        RecipeManager.saveRecipes(context, allRecipes)
    }
}
