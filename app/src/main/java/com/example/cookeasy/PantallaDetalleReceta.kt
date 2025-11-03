package com.example.cookeasy

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
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



    @SuppressLint("SetTextI18n")    //quita el subrayado amarillo  de los bindings
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

        //obtiene la receta selecccionada que adapter pantalla nos paso a traves del intent
        val recetaTitulo = intent.getStringExtra("recetaSeleccionada")

        //val receta = RecetasData.listaDeRecetas.find { it.titulo == recetaTitulo }

        val receta = RecipeManager.getRecipes(this).find { it.titulo == recetaTitulo }


        binding.recipeTitle.text = receta?.titulo
        binding.recipeTime.text = "${receta?.dificultad} - ${receta?.tiempoPreparacion}"


        binding.ingredientsList.text = "Ingredientes:\n"  +
                (receta?.ingredientes?.joinToString("\n") { "• ${it.nombre}: ${it.cantidad}" } ?: "No hay ingredientes.")

        binding.instructionsText.text = "Instrucciones:\n" +
                (receta?.instrucciones?.joinToString("\n") { "${it.numPaso}. ${it.descripcion}" } ?: "No hay instrucciones.")


        //para mostrar las imagenes de la receta seleccionada
        Glide.with(this)
            .load(receta?.imagenReceta)
            .into(binding.recipeImage)


        binding.btnBack.setOnClickListener {
            finish()
        }


        receta?.let { recetaSeleccionada ->

            // es una funcion que lee el estado actual del favorito y cambia el texto para ubicarnos mejor
            fun updateButtonState() {
                val esFav = FavoritesManager.isFavorite(context, recetaSeleccionada.NumReceta)
                binding.btnFav.text = if (esFav) "Ya es Favorito" else "Favorito"
            }

            updateButtonState()

            binding.btnFav.setOnClickListener {
                FavoritesManager.toggleFavorite(context, recetaSeleccionada.NumReceta)

                updateButtonState()
            }
            binding.btnDelete.setOnClickListener {
                RecipeManager.deleteRecipe(context, recetaSeleccionada.NumReceta)
                Toast.makeText(context, "Receta eliminada", Toast.LENGTH_SHORT).show()
                finish()
            }
            binding.btnEdit.setOnClickListener {
                val intent = Intent(context, PantallaAgregarReceta::class.java)
                intent.putExtra("RECIPE_ID_TO_EDIT", recetaSeleccionada.NumReceta)
                startActivity(intent)
                finish()
            }
        }

        }

    }
