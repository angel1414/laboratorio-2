package com.example.acividad_crud_angel_hernandez

import RecyclerViewHelpers.adaptador
import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelo.claseConexion
import modelo.dataclasstickets
import java.util.Calendar
import java.util.UUID

class inicio : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_inicio)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val txtTituloTicket = findViewById<EditText>(R.id.txtTituloTicket)
        val txtDescripcionTicket = findViewById<EditText>(R.id.txtDescripcionTicket)
        val txtAutorTicket = findViewById<EditText>(R.id.txtAutorTicket)
        val txtCorreoTicket = findViewById<EditText>(R.id.txtCorreoTicket)
        val txtInicioTicket = findViewById<EditText>(R.id.txtInicioTicket)
        val txtFinTicket = findViewById<EditText>(R.id.txtFinTicket)
        val btnSubir = findViewById<Button>(R.id.btnSubir)
        val rcvTickets = findViewById<RecyclerView>(R.id.rcvTickets)

        txtInicioTicket.setOnClickListener {
            val calndario = Calendar.getInstance()
            val ano = calndario.get(Calendar.YEAR)
            val mes = calndario.get(Calendar.MONTH)
            val dia = calndario.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = DatePickerDialog(this,
                { view, anoSeleccionado, mesSeleccionado,diaSeleccionado ->
                    val fechaSelecionada =
                        "$diaSeleccionado/${mesSeleccionado + 1}/$anoSeleccionado"
                    txtInicioTicket.setText(fechaSelecionada)

                },
                ano, mes, dia

            )
            datePickerDialog.show()
        }

        txtFinTicket.setOnClickListener {
            val calendario = Calendar.getInstance()
            val ano = calendario.get(Calendar.YEAR)
            val mes = calendario.get(Calendar.MONTH)
            val dia = calendario.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = DatePickerDialog(this,
                { view, anoSelecionado, mesSelecionado, diaSelecionado ->
                    val fechaSelecionada =
                        "$diaSelecionado/${mesSelecionado + 1}/$anoSelecionado"
                    txtFinTicket.setText(fechaSelecionada)

                },
                ano, mes, dia

            )
            datePickerDialog.show()


            btnSubir.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    val objConexion = claseConexion().cadenaConexion()

                    val anadirTicket = objConexion?.prepareStatement("insert into TabTickets (NumeroTicket, TituloTicket, Descripcion, Autor, Email, FechaDeInicio, EstadoTicket, FechaFinTicket) VALUES (?,?,?,?,?,?,?,?")!!
                    anadirTicket.setString(1,UUID.randomUUID().toString())
                    anadirTicket.setString(2,txtTituloTicket.text.toString())
                    anadirTicket.setString(3,txtDescripcionTicket.text.toString())
                    anadirTicket.setString(4,txtAutorTicket.text.toString())
                    anadirTicket.setString(5,txtCorreoTicket.text.toString())
                    anadirTicket.setString(6,txtInicioTicket.text.toString())
                    anadirTicket.setString(7,"Activo")
                    anadirTicket.setString(8,txtFinTicket.text.toString())
                    anadirTicket.executeUpdate()
                }

                rcvTickets.layoutManager = LinearLayoutManager(this)

                fun obtenerDatos(): List<dataclasstickets>{

                    val objConexion = claseConexion().cadenaConexion()

                    val statement = objConexion?.createStatement()
                    val resultSet = statement?.executeQuery("select * from TabTickets")!!

                    val ticket = mutableListOf<dataclasstickets>()

                    while (resultSet.next()){
                        val NumeroTicket = resultSet.getString("NumeroTicket")
                        val TituloTicket = resultSet.getString("TituloTicket")
                        val Descripcion = resultSet.getString("Descripcion")
                        val Autor = resultSet.getString("Autor")
                        val Email = resultSet.getString("Email")
                        val FechaDeInicio = resultSet.getString("FechaDeInicio")
                        val EstadoTicket = resultSet.getString("EstadoTicket")
                        val FechaFinTicket =resultSet.getString("FechaFinTicket")


                        val ticket = dataclasstickets(NumeroTicket, TituloTicket,Descripcion,Autor, Email, FechaDeInicio,EstadoTicket,FechaFinTicket)
                        ticket.add(ticket)
                    }
                    return ticket

                }
                CoroutineScope(Dispatchers.IO).launch {
                    val baseTickets = obtenerDatos()
                    withContext(Dispatchers.Main){
                        val adapter = adaptador(baseTickets)
                        rcvTickets.adapter= adapter
                    }
                }
            }

        }


    }
}