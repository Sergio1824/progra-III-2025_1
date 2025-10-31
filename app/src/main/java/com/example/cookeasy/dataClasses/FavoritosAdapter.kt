package com.example.cookeasy.adapters

import android.view.LayoutInflater
import com.example.cookeasy.R

internal class {
    internal inner class FavoritosAdapter
    internal inner class FavoritoViewHolder

    fun ViewHolder() {
        val titulo: `val`?
        TextView = view.findViewById(R.id.text_titulo_receta)
        val dificultad: `val`?
        TextView = view.findViewById(R.id.text_dificultad)
        val imagen: `val`?
        ImageView = view.findViewById(R.id.image_receta)
    }

    var `fun`: override? = null
    fun onCreateViewHolder()
    fun FavoritoViewHolder() {
        val view: `val`? = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_favorito, parent, false)
        return FavoritoViewHolder(view)
    }

    var `fun`: override? = null
    fun onBindViewHolder() {
        val receta: `val` = lista[position]
        holder.titulo.text = receta.titulo
        holder.dificultad.text = "\${receta.dificultad} - \${receta.tiempoPreparacion}"
        holder.imagen.setImageResource(R.drawable.placeholder_food)
    }

    var `fun`: override? = null
    val itemCount: Unit
}