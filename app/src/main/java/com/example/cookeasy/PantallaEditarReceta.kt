package com.example.cookeasy

import android.content.Context
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.cookeasy.dataClasses.Ingrediente
import com.example.cookeasy.dataClasses.Instruccion
import com.example.cookeasy.dataClasses.Receta
import com.example.cookeasy.databinding.ActivityPantallaEditarRecetaBinding // Importa el nuevo binding
import com.example.cookeasy.managers.RecipeManager

class PantallaEditarReceta : AppCompatActivity() {

    val context: Context = this

    private lateinit var binding: ActivityPantallaEditarRecetaBinding // Usa el nuevo binding
    private var recetaParaEditar: Receta? = null // Esta variable guardará la receta que estamos editando

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityPantallaEditarRecetaBinding.inflate(layoutInflater) // Usa el nuevo binding
        val view = binding.root
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Configura los spinners
        setupSpinners()

        // --- Lógica de Edición ---
        // Esta pantalla SÓLO edita, así que asumimos que SIEMPRE recibimos un ID
        val recipeIdToEdit = intent.getStringExtra("RECIPE_ID_TO_EDIT")

        if (recipeIdToEdit == null) {
            // Si por algún error no llega el ID, mostramos un error y cerramos la pantalla
            Toast.makeText(this, "Error: No se encontró la receta", Toast.LENGTH_LONG).show()
            finish()
            return // Detenemos la ejecución de onCreate
        }

        // Buscamos la receta
        recetaParaEditar = RecipeManager.getRecipes(this).find { it.NumReceta == recipeIdToEdit }

        // Si la encontramos, rellenamos el formulario
        if (recetaParaEditar != null) {
            rellenarFormulario(recetaParaEditar!!)
        } else {
            // Si el ID existe pero la receta fue borrada, mostramos error y cerramos
            Toast.makeText(this, "Error: Receta no válida", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        // Asignamos el listener al botón de guardar
        binding.btnGuardar.setOnClickListener {
            guardarCambiosEditados()
        }
    }


    private fun setupSpinners() {
        val categorias = arrayOf("Aperitivos", "Plato fuerte", "Desayuno", "Postres", "Bebidas")
        val categoriaAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categorias)
        categoriaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spCategoria.adapter = categoriaAdapter

        val dificultades = arrayOf("Fácil", "Media", "Difícil")
        val dificultadAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, dificultades)
        dificultadAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spDificultad.adapter = dificultadAdapter
    }

    // Esta función es idéntica a la que tenías
    private fun rellenarFormulario(receta: Receta) {
        binding.etNombre.setText(receta.titulo)
        binding.etTiempo.setText(receta.tiempoPreparacion)
        binding.etUrlImagen.setText(receta.imagenReceta)

        val ingredientesTexto = receta.ingredientes.joinToString("\n") { "${it.nombre}: ${it.cantidad}" }
        val instruccionesTexto = receta.instrucciones.joinToString("\n") { it.descripcion }

        binding.etIngredientes.setText(ingredientesTexto)
        binding.etInstrucciones.setText(instruccionesTexto)

        val categoriaAdapter = binding.spCategoria.adapter as ArrayAdapter<String>
        val categoriaPosition = categoriaAdapter.getPosition(receta.categoria)
        binding.spCategoria.setSelection(categoriaPosition)

        val dificultadAdapter = binding.spDificultad.adapter as ArrayAdapter<String>
        val dificultadPosition = dificultadAdapter.getPosition(receta.dificultad)
        binding.spDificultad.setSelection(dificultadPosition)
    }


    // Esta es la función de "Guardar" PERO solo para editar
    private fun guardarCambiosEditados() {

        // Primero, validamos que la receta original exista
        if (recetaParaEditar == null) {
            Toast.makeText(this, "Error al guardar, receta no encontrada", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val titulo = binding.etNombre.text.toString().trim()
        val categoria = binding.spCategoria.selectedItem.toString()
        val dificultad = binding.spDificultad.selectedItem.toString()
        val tiempo = binding.etTiempo.text.toString().trim()
        val urlImagen = binding.etUrlImagen.text.toString().trim()
        val ingredientesTexto = binding.etIngredientes.text.toString().trim()
        val instruccionesTexto = binding.etInstrucciones.text.toString().trim()

        if (titulo.isEmpty() || tiempo.isEmpty() || ingredientesTexto.isEmpty() || instruccionesTexto.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_LONG).show()
            return
        }

        val listaIngredientes = ingredientesTexto.lines().mapNotNull { linea ->
            val partes = linea.split(":")
            when {
                partes.size == 2 -> Ingrediente(partes[0].trim(), partes[1].trim())
                linea.isNotBlank() -> Ingrediente(linea.trim(), "")
                else -> null
            }
        }

        val listaInstrucciones = instruccionesTexto.lines().mapIndexedNotNull { index, descripcion ->
            if (descripcion.isNotBlank()) Instruccion(index + 1, descripcion.trim()) else null
        }

        val imagenFinal = urlImagen.ifEmpty {
            "https://st4.depositphotos.com/16122460/29909/i/450/depositphotos_299099010-stock-photo-dirty-plate-with-food-leftovers.jpg"
        }

        // 🔹 Aquí solo actualizamos la receta existente
        // Usamos .copy() sobre la receta original para mantener su ID y estado de favorito
        val recetaEditada = recetaParaEditar!!.copy(
            titulo = titulo,
            categoria = categoria,
            imagenReceta = imagenFinal,
            dificultad = dificultad,
            tiempoPreparacion = tiempo,
            ingredientes = listaIngredientes,
            instrucciones = listaInstrucciones
            // Nota: NumReceta y esFavorito se mantienen igual que la original
        )

        RecipeManager.updateRecipe(this, recetaEditada)
        Toast.makeText(this, "Cambios guardados para '${recetaEditada.titulo}'", Toast.LENGTH_SHORT).show()
        finish() // Cerramos la pantalla de edición
    }
}