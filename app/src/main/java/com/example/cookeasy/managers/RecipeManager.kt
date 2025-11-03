package com.example.cookeasy.managers

import android.content.Context
import com.example.cookeasy.dataClasses.Receta
import com.example.cookeasy.singleton.RecetasData
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


//es como el administrador, es el que sabe como hablar con sharedPreferences para leer y escribir recetas.
object RecipeManager {
    private val PREFS_NAME = "CookEasyPrefs"    //nombre del sharedPreferences
    private val RECIPES_KEY = "UserRecipesJson" //id para la lista de recetas

    private fun getPrefs(context: Context) =
        // sirve para darnos acceso al sharedpreferences llamado PREFS_NAME
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun saveRecipes(context: Context, recipes: List<Receta>) {
        val jsonString = Json.encodeToString(recipes)
        getPrefs(context).edit().putString(RECIPES_KEY, jsonString).apply()
    }   //recibe una lista nueva y esta es reemplazada por la version vieja
    //cuando toma la lista de objetos, a json

    fun getRecipes(context: Context): MutableList<Receta> {
        val jsonString = getPrefs(context).getString(RECIPES_KEY, null) ?: ""
        return if (jsonString.isNotEmpty()) {
            Json.decodeFromString<MutableList<Receta>>(jsonString)
        } else {
            mutableListOf()
        }
    }      // lee el json desde sharedPreferences y lo convierte de nuevo en una lista de objetos


    fun addRecipe(context: Context, newRecipe: Receta) {
        val currentRecipes = getRecipes(context)
        currentRecipes.add(newRecipe)
        saveRecipes(context, currentRecipes)
    }   //recibe la receta y la anade al final de la lista y la guardo  con saveRecipes
    //es para anadir una nueva receta,1. lee la lista actual, 2. anade la nueva receta a la lista
    //3. guarda la lista con la funcion ya creada antes: saveRecipe

    fun deleteRecipe(context: Context, recipeId: String) {
        val currentRecipes = getRecipes(context)
        val recipeToDelete = currentRecipes.find { it.NumReceta == recipeId }
        if (recipeToDelete != null) {
            currentRecipes.remove(recipeToDelete)
            saveRecipes(context, currentRecipes)
        }
    }        // obtiene la lista actual de nuestras recetas
    //  identifica y busca cual va a ser la receta que se va a eliminar
    //  si la encuentra y no es null la borra con remove
    //  guarda la lista actualizada con nuestra funcion ya creada

    fun updateRecipe(context: Context, updatedRecipe: Receta) {

        val currentRecipes = getRecipes(context)
        val index = currentRecipes.indexOfFirst { it.NumReceta == updatedRecipe.NumReceta }

        if (index != -1) {
            currentRecipes[index] = updatedRecipe
            saveRecipes(context, currentRecipes)
        }// obtiene la lista actual de nuestras recetas
        // encuentra la posicion de la receta vieja(la seleccioanda)
        //  si la encuentra osea no es -1, la reemplaza en esa posicion
        // guarda la lista actualizada con nuestra funcion ya creada

    }
        fun initialize(context: Context) {
            val existingRecipes = getRecipes(context)
            if (existingRecipes.isEmpty()) {
                val defaultRecipes = RecetasData.listaDeRecetas
                saveRecipes(context, defaultRecipes)
            }
        }// toma las recetas(25) predeterminadas y las guarda para crear la primera lista que se muestra.

    }//El recipe manager es el que controla las recetas, las lee y las reescribe, guarda ,etc.
