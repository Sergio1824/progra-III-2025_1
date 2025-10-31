package com.example.cookeasy

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cookeasy.R.*
import com.example.cookeasy.adapters.AdapterPantallaReceta
import com.example.cookeasy.singleton.RecetasData

class PantallaFavoritos : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AdapterPantallaReceta

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_pantalla_favoritos)

        recyclerView = findViewById(R.id.RecyclerFavoritos)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val recetasFavoritas = RecetasData.listaDeRecetas.filter { it.esFavorito }

        if (recetasFavoritas.isNotEmpty()) {
            adapter = AdapterPantallaReceta(recetasFavoritas, this)
            recyclerView.adapter = adapter
        } else {
            Toast.makeText(this, "Aún no tienes recetas favoritas ❤️", Toast.LENGTH_SHORT).show()
        }
    }
}
