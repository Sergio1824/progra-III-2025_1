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
import com.example.cookeasy.databinding.ActivityPantallaAgregarRecetaBinding // Usa el binding original
import com.example.cookeasy.managers.RecipeManager

class PantallaAgregarReceta : AppCompatActivity() {

    val context: Context = this

    private lateinit var binding: ActivityPantallaAgregarRecetaBinding // Usa el binding original


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityPantallaAgregarRecetaBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // configuracion de  los spinners
        setupSpinners()


        binding.tvTitulo.text = "Nueva Receta"
        binding.btnGuardar.text = "Guardar Receta ✅"

        binding.btnGuardar.setOnClickListener {
            guardarNuevaReceta()
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



    private fun guardarNuevaReceta() {

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


        // una receta nueva
        val nuevoId = System.currentTimeMillis().toString()

        val nuevaReceta = Receta(
            NumReceta = nuevoId,
            titulo = titulo,
            categoria = categoria,
            imagenReceta = imagenFinal,
            dificultad = dificultad,
            tiempoPreparacion = tiempo,
            ingredientes = listaIngredientes,
            instrucciones = listaInstrucciones,
            esFavorito = false
        )

        RecipeManager.addRecipe(this, nuevaReceta)
        Toast.makeText(this, "Receta '$titulo' guardada con éxito", Toast.LENGTH_SHORT).show()
        finish()
    }
}