package com.example.cookeasy

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cookeasy.adapters.FavoritosAdapter
import com.example.cookeasy.dataClasses.Receta

class PantallaFavoritos : AppCompatActivity() {

    private lateinit var recyclerFavoritos: RecyclerView
    private lateinit var adapter: FavoritosAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favoritos)

        recyclerFavoritos = findViewById(R.id.recycler_favoritos)

        val listaFavoritos = listOf(
            Receta("1", "PANCAKES", "Desayuno", "", "Medio", "30 min", listOf(), listOf(), true),
            Receta("2", "Pastel de Chocolate", "Postre", "", "Medio/difícil", "45 min", listOf(), listOf(), true),
            Receta("3", "Filete de Res", "Cena", "", "Difícil", "1 hr", listOf(), listOf(), true)
        )

        adapter = FavoritosAdapter(listaFavoritos)
        recyclerFavoritos.layoutManager = LinearLayoutManager(this)
        recyclerFavoritos.adapter = adapter

        findViewById<Button>(R.id.button_volver).setOnClickListener {
            startActivity(Intent(this, PantallaInicio::class.java))
            finish()
        }
    }
}
