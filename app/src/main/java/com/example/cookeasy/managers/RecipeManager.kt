package com.example.cookeasy.managers

import android.content.Context
import com.example.cookeasy.dataClasses.Receta
import com.example.cookeasy.singleton.RecetasData
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object RecipeManager {
    private val PREFS_NAME = "CookEasyPrefs"    //nombre del sharedPreferences
    private val RECIPES_KEY = "UserRecipesJson" //id para la lista de recetas

    private fun getPrefs(context: Context) =  // sirve para darnos acceso al sharedpreferences llamado PREFS_NAME
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun saveRecipes(context: Context, recipes: List<Receta>) {
        val jsonString = Json.encodeToString(recipes)
        getPrefs(context).edit().putString(RECIPES_KEY, jsonString).apply()
    }

    fun getRecipes(context: Context): MutableList<Receta> {
        val jsonString = getPrefs(context).getString(RECIPES_KEY, null) ?: ""
        return if (jsonString.isNotEmpty()) {
            Json.decodeFromString<MutableList<Receta>>(jsonString)
        } else {
            mutableListOf()
        }
    }

    fun addRecipe(context: Context, newRecipe: Receta) {
        val currentRecipes = getRecipes(context)
        currentRecipes.add(newRecipe)
        saveRecipes(context, currentRecipes)
    }

    fun initialize(context: Context) {
        val existingRecipes = getRecipes(context)
        if (existingRecipes.isEmpty()) {
            val defaultRecipes = RecetasData.listaDeRecetas
            saveRecipes(context, defaultRecipes)
        }
    }

}