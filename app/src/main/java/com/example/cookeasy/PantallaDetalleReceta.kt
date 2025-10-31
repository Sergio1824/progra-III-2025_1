package com.example.cookeasy

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.cookeasy.adapters.AdapterPantallaDetalleReceta
import com.example.cookeasy.databinding.ActivityPantallaDetalleRecetaBinding
import com.example.cookeasy.databinding.ActivityPantallaRecetasBinding
import com.example.cookeasy.managers.FavoritesManager
import com.example.cookeasy.managers.RecipeManager
import com.example.cookeasy.singleton.RecetasData

class PantallaDetalleReceta : AppCompatActivity() {

    private lateinit var binding: ActivityPantallaDetalleRecetaBinding

    val context: Context = this

    val adapter by lazy { AdapterPantallaDetalleReceta() }


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPantallaDetalleRecetaBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val recetaTitulo = intent.getStringExtra("recetaSeleccionada")

        //val receta = RecetasData.listaDeRecetas.find { it.titulo == recetaTitulo }

        val receta = RecipeManager.getRecipes(this).find { it.titulo == recetaTitulo }


        binding.recipeTitle.text = receta?.titulo
        binding.recipeTime.text = "${receta?.dificultad} - ${receta?.tiempoPreparacion}"




        binding.ingredientsList.text = "Ingredientes:\n" +
                (receta?.ingredientes?.joinToString("\n") { "• ${it.nombre}: ${it.cantidad}" }
                    ?: "No hay ingredientes.")

        binding.instructionsText.text = "Instrucciones:\n" +
                (receta?.instrucciones?.joinToString("\n") { "${it.numPaso}. ${it.descripcion}" }
                    ?: "No hay instrucciones.")


        Glide.with(this)
            .load(receta?.imagenReceta)
            .into(binding.recipeImage)


        binding.btnBack.setOnClickListener {
            finish()
        }

        /*        receta?.let { recetaSeleccionada ->

                    binding.btnFav.text = if (recetaSeleccionada.esFavorito) "Ya es Favorito" else "Favorito"

                    binding.btnFav.setOnClickListener {
                        recetaSeleccionada.esFavorito = !recetaSeleccionada.esFavorito

                        binding.btnFav.text = if (recetaSeleccionada.esFavorito) "Ya es Favorito" else "Favorito"
           */
        receta?.let { recetaSeleccionada ->

            fun updateButtonState() {
                val isFavorite = FavoritesManager.isFavorite(context, recetaSeleccionada.NumReceta)
                binding.btnFav.text = if (isFavorite) "Quitar de Favoritos" else "Añadir a Favoritos"
            }

            updateButtonState()

            binding.btnFav.setOnClickListener {
                FavoritesManager.toggleFavorite(context, recetaSeleccionada.NumReceta)
                updateButtonState()
            }
        }
    }
}
