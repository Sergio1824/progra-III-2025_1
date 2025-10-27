package com.example.cookeasy

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.cookeasy.databinding.ActivityPantallaLogInBinding

class PantallaLogIn : AppCompatActivity() {
    val context: Context = this
    private lateinit var binding: ActivityPantallaLogInBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityPantallaLogInBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //TO_DO LO QUE ESTA ACA ESTA DENTRO DEL ONCREATE

        binding.ButtonLogin.setOnClickListener {
            val intentCambiarAMainActivity= Intent(context, MainActivity::class.java)
            startActivity(intentCambiarAMainActivity)
        }



    }
}