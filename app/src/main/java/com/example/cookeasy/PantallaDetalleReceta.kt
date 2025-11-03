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
// OJO: Asegúrate que SÍ estás importando tus 'managers'
import com.example.cookeasy.databinding.ActivityPantallaDetalleRecetaBinding
import com.example.cookeasy.managers.FavoritesManager
import com.example.cookeasy.managers.RecipeManager

class PantallaDetalleReceta : AppCompatActivity() {

    private lateinit var binding: ActivityPantallaDetalleRecetaBinding
    private val context: Context = this

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


        // obtenemos el id que nos envio el adapter
        val recetaId = intent.getStringExtra("RECETA_ID_SELECCIONADA")

        // verificamos el id
        if (recetaId == null) {
            Toast.makeText(this, "Error: No se recibió ID de la receta", Toast.LENGTH_LONG).show()
            finish() // Cierra la pantalla si no hay ID
            return   // Detiene la ejecución de onCreate
        }

        //buscamos la recetq en el manager usando el ID
        val receta = RecipeManager.getRecipes(this).find { it.NumReceta == recetaId }

        // verificamos si la receta existe, si la encontrameos
        if (receta == null) {
            Toast.makeText(this, "Error: Receta no encontrada (ID: $recetaId)", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        //  si toodo esta bien, llenamos los datos con su titulo, y sus detalles
        binding.recipeTitle.text = receta.titulo
        binding.recipeTime.text = "${receta.dificultad} - ${receta.tiempoPreparacion}"

        val ingredientesTexto = receta.ingredientes.joinToString("\n") { "• ${it.nombre}: ${it.cantidad}" }
        binding.ingredientsList.text = "Ingredientes:\n${ingredientesTexto.ifEmpty { "No hay ingredientes." }}"

        val instruccionesTexto = receta.instrucciones.joinToString("\n") { "${it.numPaso}. ${it.descripcion}" }
        binding.instructionsText.text = "Instrucciones:\n${instruccionesTexto.ifEmpty { "No hay instrucciones." }}"

        Glide.with(this)
            .load(receta.imagenReceta)
            .into(binding.recipeImage)

        // botones del detalle receta

        binding.btnBack.setOnClickListener {
            finish()
        }

        // funcion interna para actualizar el botón de Favorito
        fun updateButtonState() {
            val esFav = FavoritesManager.isFavorite(context, receta.NumReceta)
            binding.btnFav.text = if (esFav) "Ya es Favorito" else "Favorito"
        }

        // carga el estado inicial del botón
        updateButtonState()

        binding.btnFav.setOnClickListener {
            FavoritesManager.toggleFavorite(context, receta.NumReceta)
            updateButtonState() // actualiza el texto al hacer click
        }

        binding.btnDelete.setOnClickListener {
            RecipeManager.deleteRecipe(context, receta.NumReceta)
            Toast.makeText(context, "Receta eliminada", Toast.LENGTH_SHORT).show()
            finish()
        }

        binding.btnEdit.setOnClickListener {
            // Se dirige a PantallaEditarReceta)
            val intent = Intent(context, PantallaEditarReceta::class.java)
            intent.putExtra("RECIPE_ID_TO_EDIT", receta.NumReceta)
            startActivity(intent)
        }

    }
}