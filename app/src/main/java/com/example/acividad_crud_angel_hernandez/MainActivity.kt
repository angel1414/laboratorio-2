package com.example.acividad_crud_angel_hernandez

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import modelo.claseConexion

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val txtIngresarNombre = findViewById<TextView>(R.id.txtIngresarNombre)
        val txtIngresarContrasena = findViewById<TextView>(R.id.txtIngresarContrasena)
        val btnInicar = findViewById<Button>(R.id.btnInicar)
        val btnRegistrar = findViewById<Button>(R.id.btnRegistrar)

        btnInicar.setOnClickListener {
            val inicio = Intent (this, inicio::class.java)
            GlobalScope.launch (Dispatchers.IO){
                val objConexion = claseConexion().cadenaConexion()
                val checkearUsuario = objConexion?.prepareStatement("SELECT * FROM TabUsuarios WHERE Usuario = ? AND Contrasena = ?")!!
                checkearUsuario.setString(1, txtIngresarNombre.text.toString())
                checkearUsuario.setString(2,txtIngresarContrasena.toString())
                val Resultado = checkearUsuario.executeQuery()
                if(Resultado.next()){
                    startActivity(inicio)
                }else{
                    println("No se encontro el usuario, compruebe sus credenciales")
                }
            }
        }
        btnRegistrar.setOnClickListener {
            val registrar = Intent (this@MainActivity, registrar::class.java)
        }
    }
}