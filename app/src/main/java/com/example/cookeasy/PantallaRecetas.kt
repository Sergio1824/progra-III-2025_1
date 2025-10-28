package com.example.cookeasy

import android.content.Context
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cookeasy.adapters.AdapterPantallaCategoria
import com.example.cookeasy.adapters.AdapterPantallaReceta
import com.example.cookeasy.dataClasses.Ingrediente
import com.example.cookeasy.dataClasses.Instruccion
import com.example.cookeasy.dataClasses.Receta
import com.example.cookeasy.databinding.ActivityPantallaCategoriasBinding
import com.example.cookeasy.databinding.ActivityPantallaRecetasBinding

class PantallaRecetas : AppCompatActivity() {

    val context: Context = this

    val adapter by lazy { AdapterPantallaReceta() }

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

        val listaDeRecetas = listOf(
            Receta(
                NumReceta = "1",
                titulo = "Flan de vainilla",
                categoria = "Postres",
                imagenReceta = "https://cdn7.kiwilimon.com/recetaimagen/37033/640x640/46357.jpg",
                dificultad = "Fácil",
                tiempoPreparacion = "45 min",
                ingredientes = listOf(
                    Ingrediente("Leche condensada", "1 lata"),
                    Ingrediente("Huevos", "4 unidades"),
                    Ingrediente("Azúcar", "1 taza"),
                    Ingrediente("Vainilla", "1 cucharadita")
                ),
                instrucciones = listOf(
                    Instruccion(1, "Precalienta el horno a 180°C."),
                    Instruccion(2, "Derrite el azúcar hasta formar un caramelo y viértelo en el molde."),
                    Instruccion(3, "Licúa los demás ingredientes y vierte la mezcla sobre el caramelo."),
                    Instruccion(4, "Hornea a baño maría por 40 minutos o hasta que cuaje.")
                ),
                esFavorito = true
            ),
            Receta(
                NumReceta = "2",
                titulo = "Pasta Alfredo",
                categoria = "Plato fuerte",
                imagenReceta = "https://www.recetasnestle.com.ec/sites/default/files/styles/recipe_detail_desktop/public/2022-10/pasta-alfredo_0.jpg",
                dificultad = "Media",
                tiempoPreparacion = "30 min",
                ingredientes = listOf(
                    Ingrediente("Pasta fettuccine", "200 g"),
                    Ingrediente("Mantequilla", "2 cucharadas"),
                    Ingrediente("Crema de leche", "1 taza"),
                    Ingrediente("Queso parmesano", "1/2 taza")
                ),
                instrucciones = listOf(
                    Instruccion(1, "Cocina la pasta según las instrucciones del paquete."),
                    Instruccion(2, "Derrite la mantequilla y añade la crema de leche."),
                    Instruccion(
                        3,
                        "Agrega el queso parmesano y mezcla hasta obtener una salsa cremosa."
                    ),
                    Instruccion(4, "Incorpora la pasta y mezcla bien antes de servir.")
                ),
                esFavorito = false
            ),
            Receta(
                NumReceta = "3",
                titulo = "Ensalada César",
                categoria = "Aperitivos",
                imagenReceta = "https://www.pequerecetas.com/wp-content/uploads/2019/07/ensalada-cesar-receta.jpg",
                dificultad = "Fácil",
                tiempoPreparacion = "15 min",
                ingredientes = listOf(
                    Ingrediente("Lechuga romana", "1 unidad"),
                    Ingrediente("Pollo a la plancha", "100 g"),
                    Ingrediente("Pan tostado", "1 taza"),
                    Ingrediente("Queso parmesano", "al gusto")
                ),
                instrucciones = listOf(
                    Instruccion(1, "Corta la lechuga y el pollo en trozos pequeños."),
                    Instruccion(2, "Agrega los crutones y el queso parmesano."),
                    Instruccion(3, "Añade la salsa César y mezcla bien antes de servir.")
                ),
                esFavorito = false
            ),
            Receta(
                NumReceta = "4",
                titulo = "Limonada frappé",
                categoria = "Bebidas",
                imagenReceta = "https://cdn0.recetasgratis.net/es/posts/0/6/2/limonada_frappe_10260_orig.jpg",
                dificultad = "Fácil",
                tiempoPreparacion = "10 min",
                ingredientes = listOf(
                    Ingrediente("Limones", "3 unidades"),
                    Ingrediente("Agua fría", "2 tazas"),
                    Ingrediente("Azúcar", "al gusto"),
                    Ingrediente("Hielo", "1 taza")
                ),
                instrucciones = listOf(
                    Instruccion(1, "Exprime los limones."),
                    Instruccion(2, "Lleva todos los ingredientes a la licuadora."),
                    Instruccion(3, "Licúa hasta obtener una mezcla espumosa y sirve al instante.")
                ),
                esFavorito = true
            )
        )

        adapter.addDataCards(listaDeRecetas)


        binding.tarjetaRecetas.layoutManager = LinearLayoutManager(this)
        binding.tarjetaRecetas.adapter = adapter

    }
}