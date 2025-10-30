package com.example.cookeasy

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.cookeasy.databinding.ActivityPantallaInicioBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth


class PantallaInicio : AppCompatActivity() {
    val context: Context = this


    private lateinit var binding: ActivityPantallaInicioBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding = ActivityPantallaInicioBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

        //-----------------------------------------------------------------------------------------


        binding.cerrarSesion.setOnClickListener { //el binding llama al boton de xml  cerrar sesion para ejecutar la funcion cerrar sesion usuario de esta clase.
            cerrarSesionUsuario()
        }
        binding.buttonCategories.setOnClickListener {
            irPantallaCategorias()
        }


    }

    private fun cerrarSesionUsuario() { // usa la funcion de lesloguear del firebase auth
        auth.signOut()
        val intent = Intent(context, MainActivity::class.java) // cambia de pantalla al segundo parametro del intent

        startActivity(intent)
        finish()
    }

    private fun irPantallaCategorias() {

        val intent2 = Intent(context, PantallaCategorias::class.java)
        startActivity(intent2)
    }

}