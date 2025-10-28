package com.example.cookeasy.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cookeasy.dataClasses.Receta
import com.example.cookeasy.databinding.VistasRecetasBinding

class AdapterPantallaReceta :
    RecyclerView.Adapter<AdapterPantallaReceta.EjemploCardViewHolder>() {

    private val dataCards = mutableListOf<Receta>()
    private var context: Context? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EjemploCardViewHolder {
        context = parent.context
        return EjemploCardViewHolder(
            VistasRecetasBinding.inflate(
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
    inner class EjemploCardViewHolder(private val binding: VistasRecetasBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun binding(data: Receta) {
            //      binding.textAdapterEjemplo.text = data

            binding.nombreReceta.text = data.titulo
            binding.infoReceta.text = "(${data.dificultad} - ${data.tiempoPreparacion} RECETAS)"
            binding.infoReceta.setOnClickListener {
            }

        }
    }

    fun addDataCards(list: List<Receta>) {
        dataCards.clear()
        dataCards.addAll(list)
    }

    }
