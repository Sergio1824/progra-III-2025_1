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
    // Es mejor declarar el 'context' aquí para que los listeners lo usen
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

        // --- ▼▼▼ INICIO DE LA LÓGICA IMPORTANTE ▼▼▼ ---

        // 1. Obtenemos el ID que nos envió el adaptador.
        // Todas las variables se declaran LOCALES (dentro de onCreate).
        val recetaId = intent.getStringExtra("RECETA_ID_SELECCIONADA")

        // 2. Verificamos si el ID llegó bien.
        if (recetaId == null) {
            Toast.makeText(this, "Error: No se recibió ID de la receta", Toast.LENGTH_LONG).show()
            finish() // Cierra la pantalla si no hay ID
            return   // Detiene la ejecución de onCreate
        }

        // 3. Buscamos la receta en el Manager USANDO ESE ID.
        // Esta es la ÚNICA fuente de datos.
        val receta = RecipeManager.getRecipes(this).find { it.NumReceta == recetaId }

        // 4. Verificamos si la receta se encontró.
        if (receta == null) {
            Toast.makeText(this, "Error: Receta no encontrada (ID: $recetaId)", Toast.LENGTH_LONG).show()
            finish() // Cierra si el ID no coincide con nada
            return   // Detiene la ejecución
        }

        // 5. Si todo está bien, 'receta' es la correcta. Rellenamos los datos.
        // NO usamos 'receta?.titulo' porque ya comprobamos que 'receta' no es null.
        binding.recipeTitle.text = receta.titulo
        binding.recipeTime.text = "${receta.dificultad} - ${receta.tiempoPreparacion}"

        val ingredientesTexto = receta.ingredientes.joinToString("\n") { "• ${it.nombre}: ${it.cantidad}" }
        binding.ingredientsList.text = "Ingredientes:\n${ingredientesTexto.ifEmpty { "No hay ingredientes." }}"

        val instruccionesTexto = receta.instrucciones.joinToString("\n") { "${it.numPaso}. ${it.descripcion}" }
        binding.instructionsText.text = "Instrucciones:\n${instruccionesTexto.ifEmpty { "No hay instrucciones." }}"

        Glide.with(this)
            .load(receta.imagenReceta)
            .into(binding.recipeImage)

        // --- Lógica de los botones ---

        binding.btnBack.setOnClickListener {
            finish()
        }

        // Función interna para actualizar el botón de Favorito
        fun updateButtonState() {
            val esFav = FavoritesManager.isFavorite(context, receta.NumReceta)
            binding.btnFav.text = if (esFav) "Ya es Favorito" else "Favorito"
        }

        // Carga el estado inicial del botón
        updateButtonState()

        binding.btnFav.setOnClickListener {
            FavoritesManager.toggleFavorite(context, receta.NumReceta)
            updateButtonState() // Actualiza el texto al hacer click
        }

        binding.btnDelete.setOnClickListener {
            RecipeManager.deleteRecipe(context, receta.NumReceta)
            Toast.makeText(context, "Receta eliminada", Toast.LENGTH_SHORT).show()
            finish()
        }

        binding.btnEdit.setOnClickListener {
            // Se dirige a PantallaEditarReceta (¡Correcto!)
            val intent = Intent(context, PantallaEditarReceta::class.java)
            intent.putExtra("RECIPE_ID_TO_EDIT", receta.NumReceta)
            startActivity(intent)
        }

        // --- ▲▲▲ FIN DE LA LÓGICA IMPORTANTE ▲▲▲ ---
    }
}