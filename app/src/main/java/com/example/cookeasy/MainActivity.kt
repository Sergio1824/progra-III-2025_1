package com.example.cookeasy

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializar Firebase Auth
        auth = Firebase.auth

        // Obtener referencias a los views
        val botonLogin = findViewById<Button>(R.id.login)
        val botonCrear = findViewById<Button>(R.id.crearcuenta)
        val editCorreo = findViewById<EditText>(R.id.edit_correo)
        val editPass = findViewById<EditText>(R.id.edit_pass)

        // Listener para Login
        botonLogin.setOnClickListener {
            val correo = editCorreo.text.toString().trim()
            val pass = editPass.text.toString().trim()

            if (correo.isNotEmpty() && pass.isNotEmpty()) {
                loginValidation(correo, pass)
            } else {
                Toast.makeText(this, "Debe ingresar Correo y contraseña", Toast.LENGTH_SHORT).show()
            }
        }

        // Listener para Crear Cuenta
        botonCrear.setOnClickListener {
            val correo = editCorreo.text.toString().trim()
            val pass = editPass.text.toString().trim()

            if (pass.length >= 8 && correo.isNotEmpty()) {
                crearUsuario(correo, pass)
            } else {
                Toast.makeText(this, "Debe ingresar una contraseña de 8 o más dígitos y debe ingresar un correo", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        // Verificar si hay usuario logueado
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val intentPantallaInicio = Intent(this, PantallaInicio::class.java)
            startActivity(intentPantallaInicio)
            finish() // Cierra MainActivity para que no se pueda volver atrás
        }
    }

    // Función para crear usuario
    private fun crearUsuario(correo: String, pass: String) {
        auth.createUserWithEmailAndPassword(correo, pass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Usuario creado exitosamente → ir a PantallaInicio
                    val intentPantallaInicio = Intent(this, PantallaCrearUsuario::class.java)
                    startActivity(intentPantallaInicio)
                    finish()
                } else {
                    // Error al crear usuario
                    Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    // Función para validar login
    private fun loginValidation(correo: String, pass: String) {
        auth.signInWithEmailAndPassword(correo, pass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Login exitoso → ir a PantallaInicio
                    val intentPantallaInicio = Intent(this, PantallaInicio::class.java)
                    startActivity(intentPantallaInicio)
                    finish()
                } else {
                    // Error en login
                    Toast.makeText(baseContext, "No pudimos loguear ese usuario y contraseña", Toast.LENGTH_LONG).show()
                }
            }
    }
}