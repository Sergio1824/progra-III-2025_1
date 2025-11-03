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
import com.example.cookeasy.managers.RecipeManager
import com.example.cookeasy.singleton.RecetasData


//los adapters con para conectar, en este caso la lista de Categorias con el recyclerView de vistas_categorias
class AdapterPantallaCategoria :
    RecyclerView.Adapter<AdapterPantallaCategoria.EjemploCardViewHolder>() {

    private val dataCards = mutableListOf<Categoria>()
    private var context: Context? = null


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


            // esto llama al Manager para contar cuantas recetas estan en la ultima lista guardada
            // que pertenecen a la categoria definida,.
            val cantidadRecetas1 = RecipeManager.getRecipes(context!!).count {
                it.categoria == data.nombre
            }//usamos el recipeManager(sharedPreferences)


            //mostrar el texto para cada categoria, su nombre y su cantidad de recetas que hay en cada categorai

            binding.nombreCategoria.text = data.nombre
            binding.contadorRecetas.text = "(${cantidadRecetas1} RECETAS)"


            //para cargar las imagenes de cada categoria  (glide)
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
