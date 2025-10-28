    package com.example.cookeasy

    import android.content.Context
    import android.content.Intent
    import android.os.Bundle
    import androidx.activity.enableEdgeToEdge
    import androidx.appcompat.app.AppCompatActivity
    import androidx.core.view.ViewCompat
    import androidx.core.view.WindowInsetsCompat
    import androidx.recyclerview.widget.LinearLayoutManager
    import com.example.cookeasy.adapters.AdapterPantallaCategoria
    import com.example.cookeasy.dataClasses.Categoria
    import com.example.cookeasy.databinding.ActivityPantallaCategoriasBinding

    class PantallaCategorias : AppCompatActivity() {

        val context: Context = this


        val adapter by lazy { AdapterPantallaCategoria() }

        private lateinit var binding: ActivityPantallaCategoriasBinding


        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            enableEdgeToEdge()


            binding = ActivityPantallaCategoriasBinding.inflate(layoutInflater)
            val view = binding.root
            setContentView(view)

            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }


            val listaDeCategorias = listOf(
                Categoria(
                    numCategoria = 1,
                    nombre = "Aperitivos",
                    imagenCategoria = "https://www.hola.com/horizon/43/5f8e73a5cb9b-stockfood60232389-copia.jpg",
                    cantidadRecetas = 15
                ),
                Categoria(
                    numCategoria = 2,
                    nombre = "Plato fuerte",
                    imagenCategoria = "https://cdn.bolivia.com/images/v2/gastronomia/mundo/comida-de-mar.jpg",
                    cantidadRecetas = 42
                ),
                Categoria(
                    numCategoria = 3,
                    nombre = "Desayuno",
                    imagenCategoria = "https://comedera.com/wp-content/uploads/sites/9/2022/12/Desayono-americano-shutterstock_2120331371.jpg",
                    cantidadRecetas = 18
                ),
                Categoria(
                    numCategoria = 4,
                    nombre = "Postres",
                    imagenCategoria = "https://mott.pe/noticias/wp-content/uploads/2016/05/Los-20-postres-m%C3%A1s-famosos-del-mundo-que-tienes-que-comer-antes-de-morir-portada-mouse-chocolate-1280x720.jpg",
                    cantidadRecetas = 75
                ),
                Categoria(
                    numCategoria = 5,
                    nombre = "Bebidas",
                    imagenCategoria = "https://7239d2c8.delivery.rocketcdn.me/wp-content/uploads/2021/06/HRS-Beverages-1140x600-1.png",
                    cantidadRecetas = 22
                )
            )

            adapter.addDataCards(listaDeCategorias)


            binding.tarjetaCategorias.layoutManager = LinearLayoutManager(this)
            binding.tarjetaCategorias.adapter = adapter



            binding.titleCategory.setOnClickListener {
                val intentCambiarAMainActivity= Intent(context, PantallaRecetas::class.java)
                startActivity(intentCambiarAMainActivity)
            }

        }



    }