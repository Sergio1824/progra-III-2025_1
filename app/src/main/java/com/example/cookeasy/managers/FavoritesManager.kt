package com.example.cookeasy.managers

import android.content.Context

object FavoritesManager {
    private val PREFS_NAME: String = "CookEasyPrefs"  //este es el nombre que le ponemso  al sharedPreferences
    private val FAVORITES_KEY:String  = "FavoriteRecipes" //este es el id especifico para la lista de favoritos

    private fun getPrefs(context: Context) =  // sirve para darnos acceso al sharedpreferences llamado PREFS_NAME
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun getFavorites(context: Context): MutableSet<String> {
        val prefs = getPrefs(context)
        return prefs.getStringSet(FAVORITES_KEY, emptySet())?.toMutableSet() ?: mutableSetOf()
    }   // esta es la funcion    que nos sirve para leer la lista

    private fun saveFavorites(context: Context, favorites: Set<String>) {
        val editor = getPrefs(context).edit()
        editor.putStringSet(FAVORITES_KEY, favorites)
        editor.apply() // .apply() guarda los cambios de forma asincrona.
    }   //esta es la funcion que guarda la lista de favoritos bajo la etiqueta: FAVORITES_KEY, reemplazando la
    //lista que existiera antes

    fun addFavorite(context: Context, recipeId: String) {
        val favorites = getFavorites(context)   //lee la lista actual
        favorites.add(recipeId)     //anade el nuevo id de la receta
        saveFavorites(context, favorites)   // guarda la lista
    }

    fun removeFavorite(context: Context, recipeId: String) {
        val favorites = getFavorites(context)
        favorites.remove(recipeId)  //quita el id de la receta
        saveFavorites(context, favorites)
    }

    fun isFavorite(context: Context, recipeId: String): Boolean {
        return getFavorites(context).contains(recipeId)
    }// lee la lista y verifica si el id esta en la lista

    fun toggleFavorite(context: Context, recipeId: String) {
        if (isFavorite(context, recipeId)) {    //si ya es favorito, lo quita de favoritos
            removeFavorite(context, recipeId)
        } else {                                /// si no es favorito, lo anade a favoritos
            addFavorite(context, recipeId)
        }
    }
}
// GENERAL: lo que hace el Favorites Manager es: llevar la cuenta de que recetas son favoritas por el usuario