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

// Esta pantalla permite al usuario buscar recetas por nombre
class PantallaBusqueda : AppCompatActivity() {

    val context: Context = this

    // Reutilizamos el mismo adaptador que 'PantallaRecetas'
    val adapter by lazy { AdapterPantallaReceta() }

    private lateinit var binding: ActivityPantallaBusquedaBinding

    // Guardamos la lista completa en memoria para filtrar rápido
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

        // 1. Cargar la lista completa de recetas UNA SOLA VEZ
        listaCompletaDeRecetas = RecipeManager.getRecipes(context)

        // 2. Configurar el RecyclerView
        binding.tarjetaBusqueda.layoutManager = LinearLayoutManager(this)
        binding.tarjetaBusqueda.adapter = adapter

        //para mostrar la lista entera dee recetas:
        adapter.addDataCards(listaCompletaDeRecetas)

        // 3. Configurar el listener del SearchView
        setupSearchView()
    }

    /* Configura el listener para la barra de búsqueda (SearchView).
    */
    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            // Se llama cuando el usuario presiona "Enter"
            override fun onQueryTextSubmit(query: String?): Boolean {
                filterRecipes(query)
                return true
            }

            // Se llama CADA VEZ que el usuario escribe o borra una letra
            override fun onQueryTextChange(newText: String?): Boolean {
                filterRecipes(newText)
                return true
            }
        })
    }

    /* Filtra la lista de recetas basado en el texto de búsqueda.
    */
    private fun filterRecipes(query: String?) {
        val listaFiltrada: List<Receta>

        if (query.isNullOrBlank()) {
            // --- ESTE ES EL CAMBIO ---
            // Si la barra de búsqueda está vacía,
            // la lista filtrada es la lista COMPLETA.
            listaFiltrada = listaCompletaDeRecetas
            // --- FIN DEL CAMBIO ---

            binding.tvNoResults.visibility = View.GONE
            binding.tarjetaBusqueda.visibility = View.VISIBLE

        } else {
            // Si hay texto, filtra como antes
            val queryLower = query.lowercase()
            listaFiltrada = listaCompletaDeRecetas.filter { receta ->
                receta.titulo.lowercase().contains(queryLower)
            }

            // Lógica para mostrar "No se encontraron resultados"
            if (listaFiltrada.isEmpty()) {
                binding.tvNoResults.visibility = View.VISIBLE
                binding.tarjetaBusqueda.visibility = View.GONE
            } else {
                binding.tvNoResults.visibility = View.GONE
                binding.tarjetaBusqueda.visibility = View.VISIBLE
            }
        }
        // Actualizamos el adaptador con los resultados
        adapter.addDataCards(listaFiltrada)
    }
}