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
import com.example.cookeasy.databinding.ActivityPantallaAgregarRecetaBinding
import com.example.cookeasy.managers.RecipeManager

//import com.tuapp.databinding.ActivityPantallaAgregarRecetaBinding

class PantallaAgregarReceta : AppCompatActivity() {

    val context: Context = this

    private lateinit var binding: ActivityPantallaAgregarRecetaBinding
    private var recetaParaEditar: Receta? = null


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


          // es la configuracion de los menus de eleccion desplegables para las categorias y la dificultad
        setupSpinners()


        val recipeIdToEdit = intent.getStringExtra("RECIPE_ID_TO_EDIT")

        if (recipeIdToEdit != null) {
            // MODO DE EDICION
            // busca la receta para editar en el manager de recetas
            recetaParaEditar = RecipeManager.getRecipes(this).find { it.NumReceta == recipeIdToEdit }

            // si lo encontramos, rellena el formulario
            if (recetaParaEditar != null) {
                // cambia el texto que esta en el titulo y en el boton
                binding.tvTitulo.text = "Editar Receta"
                binding.btnGuardar.text = "Guardar Cambios"
                // llama a la funcion para llenar los espacios
                rellenarFormulario(recetaParaEditar!!)
            }
        } else {
            // vuelve t odo a lo que estaba antes
            binding.tvTitulo.text = "Nueva Receta"
            binding.btnGuardar.text = "Guardar Receta ✅"
        }


        binding.btnGuardar.setOnClickListener {
            guardarNuevaReceta()
        }
    }


    private fun setupSpinners() {
        //datos para el spinner
        //array adapter conecta la lista de datos simples con el componente de xml (spinenr)
        val categorias = arrayOf("Aperitivos", "Plato fuerte", "Desayuno", "Postres", "Bebidas")
        val categoriaAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categorias)
        categoriaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spCategoria.adapter = categoriaAdapter//para decirle que sea el traductor para obtener los datos a mostrar

        // datos para el spinner de dificultad
        val dificultades = arrayOf("Fácil", "Media", "Difícil")
        val dificultadAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, dificultades)
        dificultadAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spDificultad.adapter = dificultadAdapter
    }


    private fun rellenarFormulario(receta: Receta) {
        binding.etNombre.setText(receta.titulo)
        binding.etTiempo.setText(receta.tiempoPreparacion)
        binding.etUrlImagen.setText(receta.imagenReceta)

        // convierte las listas a texto
        val ingredientesTexto = receta.ingredientes.joinToString("\n") { "${it.nombre}: ${it.cantidad}" }
        val instruccionesTexto = receta.instrucciones.joinToString("\n") { it.descripcion }

        binding.etIngredientes.setText(ingredientesTexto)
        binding.etInstrucciones.setText(instruccionesTexto)

        // selecciona los valores de los spinneres
        val categoriaAdapter = binding.spCategoria.adapter as ArrayAdapter<String>
        val categoriaPosition = categoriaAdapter.getPosition(receta.categoria)
        binding.spCategoria.setSelection(categoriaPosition)

        val dificultadAdapter = binding.spDificultad.adapter as ArrayAdapter<String>
        val dificultadPosition = dificultadAdapter.getPosition(receta.dificultad)
        binding.spDificultad.setSelection(dificultadPosition)
    }



    private fun guardarNuevaReceta() {

        // obtiene los datos que le pasamos mediante nuestra pantalla del xml
        // trim para quitar espacios blancos
        val titulo = binding.etNombre.text.toString().trim()
        val categoria = binding.spCategoria.selectedItem.toString()
        val dificultad = binding.spDificultad.selectedItem.toString()
        val tiempo = binding.etTiempo.text.toString().trim()
        val urlImagen = binding.etUrlImagen.text.toString().trim()
        val ingredientesTexto = binding.etIngredientes.text.toString().trim()
        val instruccionesTexto = binding.etInstrucciones.text.toString().trim()

        // verificar que los espacios no esten vacias, (titulo) y (tiempo)
        if (titulo.isEmpty() || tiempo.isEmpty() || ingredientesTexto.isEmpty() || instruccionesTexto.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_LONG).show()

            // Opcional: mostrar error en el campo específico
            if (titulo.isEmpty()) binding.etNombre.error = "Obligatorio"
            if (tiempo.isEmpty()) binding.etTiempo.error = "Obligatorio"

            return
        }

        // convertir el texto de ingredientes y de instrucciones a lista de List<Ingrediente>

        // convertimos el texto de ingredientes por linea en una List<Ingrediente>
        // mapNotNull para ignorar las lineas que esten vacias
        val listaIngredientes = ingredientesTexto.lines().mapNotNull { linea ->
            val partes = linea.split(":")
            if (partes.size == 2) {
                Ingrediente(partes[0].trim(), partes[1].trim())
            } else if (linea.isNotBlank()) {
                Ingrediente(linea.trim(), "")
            } else {
                null //si esta vacio lo ignora
            }
        }

        // convertimos el texto de instrucciones por linea en una List<Ingrediente>
        val listaInstrucciones = instruccionesTexto.lines().mapIndexedNotNull { index, descripcion ->
            if (descripcion.isNotBlank()) {
                Instruccion(index + 1, descripcion.trim())
            } else {
                null
            }
        }


            // crear el objecto Receta

        // generamos un id para la nueva receta y sumamos 100 para no chocar con otros id's ya creados
        val nuevoId = (RecipeManager.getRecipes(this).size + 100).toString()

        // Usamos la URL de imagen que puso el usuario, o una por defecto si esta vacai
        val imagenFinal = urlImagen.ifEmpty { "https://st4.depositphotos.com/16122460/29909/i/450/depositphotos_299099010-stock-photo-dirty-plate-with-food-leftovers.jpg" } // URL de un plato por defecto

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

        // guardamos la nueva receta utilizando la funcion creada en nuestro manager de recetas.
        RecipeManager.addRecipe(this, nuevaReceta)

        // nos muestra el mensaje del nombre de la receta y que se creo con exit[o
        Toast.makeText(this, "Receta '$titulo' guardada con éxito", Toast.LENGTH_SHORT).show()
        finish() // terminamos de crear toda la receta y regresemos a la pantalla de inicio
    }
}

