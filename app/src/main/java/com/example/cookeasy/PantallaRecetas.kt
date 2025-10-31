package com.example.cookeasy

import android.content.Context
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cookeasy.adapters.AdapterPantallaReceta
import com.example.cookeasy.databinding.ActivityPantallaRecetasBinding
import com.example.cookeasy.singleton.RecetasData

class PantallaRecetas : AppCompatActivity() {

    val context: Context = this

    val adapter by lazy { AdapterPantallaReceta(recetasFavoritas, this) }

    private lateinit var binding: ActivityPantallaRecetasBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityPantallaRecetasBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val categoriaSeleccionada = intent.getStringExtra("categoriaSeleccionada")
        binding.titleRecipe.text = categoriaSeleccionada ?: "RECETAS"


        val recetasFiltradas = RecetasData.listaDeRecetas.filter {
            it.categoria.equals(categoriaSeleccionada, ignoreCase = true)
        }

        adapter.addDataCards(recetasFiltradas)


        binding.tarjetaRecetas.layoutManager = LinearLayoutManager(this)
        binding.tarjetaRecetas.adapter = adapter

    }
}