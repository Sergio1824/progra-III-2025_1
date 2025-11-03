package com.example.cookeasy

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cookeasy.adapters.AdapterPantallaReceta
import com.example.cookeasy.databinding.ActivityPantallaFavoritosBinding
import com.example.cookeasy.managers.RecipeManager

// esta es la pantalla que nos mostrara las recetas marcadas como favoritas
class PantallaFavoritos : AppCompatActivity() {

    val context: Context = this

    // volvemos a utilizar el adapter que cramos para las recetas
    val adapter by lazy { AdapterPantallaReceta() }

    private lateinit var binding: ActivityPantallaFavoritosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityPantallaFavoritosBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.titleFavoritos.text = "Mis Favoritos"

        // configuracion del recycler vieww
        binding.tarjetaFavoritos.layoutManager = LinearLayoutManager(this)
        binding.tarjetaFavoritos.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        //     on resume para que la lista se actualice, cada vez que volvemos a la pantalla
        loadFavoriteRecipes()
    }

    // obtiene las recetas y las filtra por las que tienen marcado favorito y las muestra con el adapter
    fun loadFavoriteRecipes() {
        //ll ama al manager para obtener nuestras recetas guardadas
        val allRecipes = RecipeManager.getRecipes(context)

        // 2. Filtra la lista para obtener SOLO las favoritas
        val favoriteRecipes = allRecipes.filter {
            it.esFavorito  == true //verifica si la receta es true ( es favorita)
        }

        // 3. Pasa la lista filtrada al adaptador
        adapter.addDataCards(favoriteRecipes)


    }
}