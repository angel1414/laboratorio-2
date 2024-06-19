package com.example.acividad_crud_angel_hernandez

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelo.claseConexion

class regitrar : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_regitrar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val txtRegistroUsuario = findViewById<TextView>(R.id.txtRegitroUsuario)
        val txtRegistroContrasena = findViewById<TextView>(R.id.txtRegistroContrasena)
        val btnRegistroFinal = findViewById<Button>(R.id.btnRegistroFinal)

        btnRegistroFinal.setOnClickListener {
             val login = Intent(this,MainActivity::class.java)
            GlobalScope.launch (Dispatchers.IO){
                val objConexion = claseConexion().cadenaConexion()

                val registroDeUsuario = objConexion?.prepareStatement("Insert into TabUsuarios(Usuario,Contrasena) VALUES (?,?)")!!
                registroDeUsuario.setString(1,txtRegistroUsuario.text.toString())
                registroDeUsuario.setString(2,txtRegistroContrasena.text.toString())
                registroDeUsuario.executeUpdate()
                withContext(Dispatchers.Main){
                    Toast.makeText(this@regitrar,"Usuario guardado", Toast.LENGTH_SHORT).show()
                    startActivity(login)
                }
            }
        }
    }
}