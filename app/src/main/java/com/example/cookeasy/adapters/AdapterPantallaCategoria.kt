package com.example.cookeasy.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cookeasy.databinding.ActivityPantallaCategoriasBinding


class AdapterPantallaCategoria :
    RecyclerView.Adapter<AdapterPantallaCategoria.EjemploCardViewHolder>() {

    private val dataCards = mutableListOf<String>()
    private var context: Context? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EjemploCardViewHolder {
        context = parent.context
        return EjemploCardViewHolder(
            ActivityPantallaCategoriasBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: EjemploCardViewHolder, position: Int) {
        holder.binding(dataCards[position])
    }

    override fun getItemCount(): Int = dataCards.size

    // Donde hacer la logica
    inner class EjemploCardViewHolder(private val binding: ActivityPantallaCategoriasBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun binding(data: String) {
      //      binding.textAdapterEjemplo.text = data



        }
    }

    fun addDataCards(list: List<String>) {
        dataCards.clear()
        dataCards.addAll(list)
    }
}
