package com.tuapp

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.cookeasy.databinding.ActivityPantallaAgregarRecetaBinding
//import com.tuapp.databinding.ActivityPantallaAgregarRecetaBinding

class PantallaAgregarReceta : AppCompatActivity() {

    private lateinit var binding: ActivityPantallaAgregarRecetaBinding
    private var imagenUri: Uri? = null

    // Content Picker para seleccionar imagen
    private val seleccionarImagen = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            imagenUri = result.data?.data
            Toast.makeText(this, "Imagen seleccionada ✅", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPantallaAgregarRecetaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar Spinner
        val categorias = listOf("Postre", "Bebida", "Entrada", "Plato Fuerte", "Otro")
        val adaptador = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, categorias)
        binding.spCategoria.adapter = adaptador

        // Botón seleccionar imagen
        binding.btnSeleccionarImagen.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK).apply { type = "image/*" }
            seleccionarImagen.launch(intent)
        }

        // Botón guardar
        binding.btnGuardar.setOnClickListener { guardarReceta() }
    }

    private fun guardarReceta() {
        val nombre = binding.etNombre.text.toString().trim()
        val categoria = binding.spCategoria.selectedItem.toString()
        val ingredientes = binding.etIngredientes.text.toString().trim()
        val instrucciones = binding.etInstrucciones.text.toString().trim()

        if (nombre.isEmpty() || ingredientes.isEmpty() || instrucciones.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos 📋", Toast.LENGTH_SHORT).show()
            return
        }

        // Aquí podrías guardar los datos en almacenamiento local (SharedPreferences o JSON)
        Toast.makeText(this, "Receta guardada 🍽️", Toast.LENGTH_LONG).show()

        // Finaliza la Activity
        finish()
    }
}
