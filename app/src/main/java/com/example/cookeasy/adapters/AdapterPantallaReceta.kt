package com.example.cookeasy.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cookeasy.PantallaDetalleReceta
import com.example.cookeasy.PantallaRecetas
import com.example.cookeasy.dataClasses.Receta
import com.example.cookeasy.databinding.VistasRecetasBinding
import kotlin.collections.addAll
import kotlin.text.clear



//los adapters con para conectar, en este caso la lista de Recetas con el recyclerView de vistas_recetas
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



            //mostrar el texto para cada receta, su nombre y su informacion
            binding.nombreReceta.text = data.titulo
            binding.infoReceta.text = "(${data.dificultad} - ${data.tiempoPreparacion})"



            //para cargar las imagenes de cada receta  (glide)

            context?.let {
                Glide.with(it) // Carga en el contexto
                    .load(data.imagenReceta) //esta es la url de la clas
                    .into(binding.imagenReceta) // es la id del image view
            }


            binding.root.setOnClickListener {
                val intent = Intent(context, PantallaDetalleReceta::class.java)
                intent.putExtra("recetaSeleccionada", data.titulo)
                context?.startActivity(intent)
            }

        }

        }

    fun addDataCards(list: List<Receta>) {
        dataCards.clear()
        dataCards.addAll(list)
    }

    }


