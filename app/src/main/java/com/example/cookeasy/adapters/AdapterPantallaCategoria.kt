package com.example.cookeasy.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cookeasy.PantallaRecetas
import com.example.cookeasy.dataClasses.Categoria
import com.example.cookeasy.databinding.VistasCategoriasBinding
import com.example.cookeasy.singleton.RecetasData


class AdapterPantallaCategoria :
    RecyclerView.Adapter<AdapterPantallaCategoria.EjemploCardViewHolder>() {

    private val dataCards = mutableListOf<Categoria>()
    private var context: Context? = null

    private var onItemClick: ((Categoria) -> Unit)? = null

    // méto_do público para configurar el clic desde fuera



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EjemploCardViewHolder {
        context = parent.context
        return EjemploCardViewHolder(
            VistasCategoriasBinding.inflate(
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
    inner class EjemploCardViewHolder(private val binding: VistasCategoriasBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun binding(data: Categoria) {
      //      binding.textAdapterEjemplo.text = data
            val cantidadRecetas1 = RecetasData.listaDeRecetas.count(){
                it.categoria == data.nombre
            }

            binding.nombreCategoria.text = data.nombre
            binding.contadorRecetas.text = "({$cantidadRecetas1} RECETAS)"

            context?.let {
                Glide.with(it) // Carga en el contexto
                    .load(data.imagenCategoria) //esta es la url de la clas
                    .into(binding.imagenCategoria) // es la id del image view
            }


            binding.root.setOnClickListener {
                val intent = Intent(context, PantallaRecetas::class.java)
                intent.putExtra("categoriaSeleccionada", data.nombre)
                context?.startActivity(intent)
            }


        }
    }

    fun addDataCards(list: List<Categoria>) {
        dataCards.clear()
        dataCards.addAll(list)
    }


}
