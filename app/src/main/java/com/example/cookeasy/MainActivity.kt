package com.example.cookeasy

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    val context: Context = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        auth = Firebase.auth
        // Obtener referencias a los views
        val botonLogin = findViewById<Button>(R.id.login)
        val botonCrear = findViewById<Button>(R.id.crearcuenta)
        val editCorreo = findViewById<EditText>(R.id.edit_correo)
        val editPass = findViewById<EditText>(R.id.edit_pass)

        // Listener para Login
        botonLogin.setOnClickListener {
            val correo = editCorreo.text.toString()
            val pass = editPass.text.toString()

            if (correo.isNotEmpty() && pass.isNotEmpty()) {
                loginValidation(correo, pass)
            } else {
                Toast.makeText(
                    baseContext,
                    "Debe ingresar Correo y contraseña",
                    Toast.LENGTH_SHORT,
                ).show()
            }

        }

        // Listener para Crear Cuenta
        botonCrear.setOnClickListener {
            val correo = editCorreo.text.toString()
            val pass = editPass.text.toString()

            if (pass.length < 8 || correo == ""){
                Toast.makeText(
                    baseContext,
                    "Debe ingresar una contraseña de 8 o mas digitos y debe ingresar un correo",
                    Toast.LENGTH_SHORT,
                ).show()
            } else {
                crearUsuario(correo, pass)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        // Verificar si hay usuario logueado // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val intentPantallaInicio = Intent(context, PantallaInicio::class.java)
            startActivity(intentPantallaInicio)
        }
    }

    // Función para crear usuario
    private fun crearUsuario(correo: String, pass: String) {
        auth.createUserWithEmailAndPassword(correo, pass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Almacenar datos //duda // Usuario creado exitosamente → ir a PantallaInicio
                    val intentPantallaInicio = Intent(context, PantallaCrearUsuario::class.java)
                    startActivity(intentPantallaInicio)
                    finish()
                } else {
                    Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    // Función para validar login
    private fun loginValidation(correo: String, pass: String) {
        auth.signInWithEmailAndPassword(correo, pass)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // El usuario se logueo correctamente // Login exitoso → ir a PantallaInicio

                } else {
                    // Error en login
                    Toast.makeText(
                            baseContext,
                        "No pudimos loguear ese usuario y contraseña",
                        Toast.LENGTH_LONG).show()
                }
            }
    }
}