package com.example.cookeasy

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView // Importante
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cookeasy.adapters.AdapterPantallaReceta // Reutilizamos el adaptador
import com.example.cookeasy.dataClasses.Receta // Importamos Receta
import com.example.cookeasy.databinding.ActivityPantallaBusquedaBinding // Binding de tu nuevo XML
import com.example.cookeasy.managers.RecipeManager

// esta pantalla permite al usuario buscar recetas por nombre
class PantallaBusqueda : AppCompatActivity() {

    val context: Context = this

    // Reutilizamos el mismo adaptador que 'PantallaRecetas'
    val adapter by lazy { AdapterPantallaReceta() }

    private lateinit var binding: ActivityPantallaBusquedaBinding

    // gguardamos la lista completa en memoria para filtrar rapido
    private var listaCompletaDeRecetas = listOf<Receta>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityPantallaBusquedaBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //cargar la lista completa de recetas la que esta en singletton
        listaCompletaDeRecetas = RecipeManager.getRecipes(context)

        // esta es la configuracion del recycler view
        binding.tarjetaBusqueda.layoutManager = LinearLayoutManager(this)
        binding.tarjetaBusqueda.adapter = adapter

        //para mostrar la lista entera dee recetas:
        adapter.addDataCards(listaCompletaDeRecetas)

        setupSearchView()
    }

    //configura el listener para la barra de bsuqueda

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            // Se llama cuando el usuario presiona "Enter"
            override fun onQueryTextSubmit(query: String?): Boolean {
                filterRecipes(query)
                return true
            }

            // Se llama cada vez que el usuario va escribiendo
            override fun onQueryTextChange(newText: String?): Boolean {
                filterRecipes(newText)
                return true
            }
        })
    }

    // filtra la lista de recetas basado en el texto de busqueda.

    private fun filterRecipes(query: String?) {
        val listaFiltrada: List<Receta>

        if (query.isNullOrBlank()) {
            //si nuestra lista esta vacia, muestra todas las recetas
            listaFiltrada = listaCompletaDeRecetas

            binding.tvNoResults.visibility = View.GONE
            binding.tarjetaBusqueda.visibility = View.VISIBLE

        } else {
            // si hay texto, filtra como antes
            val queryLower = query.lowercase()
            listaFiltrada = listaCompletaDeRecetas.filter { receta ->
                receta.titulo.lowercase().contains(queryLower)
            }

            // logica para mostrar "No se encontraron resultados"
            if (listaFiltrada.isEmpty()) {
                binding.tvNoResults.visibility = View.VISIBLE
                binding.tarjetaBusqueda.visibility = View.GONE
            } else {
                binding.tvNoResults.visibility = View.GONE
                binding.tarjetaBusqueda.visibility = View.VISIBLE
            }
        }
        // actualizamos el adapter con los resultados
        adapter.addDataCards(listaFiltrada)
    }
}