package RecyclerViewHelpers

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewParent
import androidx.recyclerview.widget.RecyclerView
import com.example.acividad_crud_angel_hernandez.R
import com.example.acividad_crud_angel_hernandez.ticket
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import modelo.claseConexion
import modelo.dataclasstickets
import java.io.DataOutputStream
import java.lang.reflect.Type
import java.text.FieldPosition

class adaptador (private var Datos: List<dataclasstickets>): RecyclerView.Adapter<ViewHolder>(){
   fun actualizarLista(nuevaLista:List<dataclasstickets>){
       Datos = nuevaLista
       notifyDataSetChanged()
   }
}

fun eliminarDatos(tituloticket: String,posicion: Int){
    val listaDatos = Datos.toMubtableList()
    listaDatos.removeAt(posicion)

    GlobalScope.launch (Dispatchers.IO){
        val objConexion = claseConexion().cadenaConexion()

        val eliminarticket = objConexion.prepareStatement("delete from TabTickets where TituloTicket = ?")!!
        eliminarticket.setString(1,tituloticket)
        eliminarticket.executeUpdate()


    }
    Datos = ListaDatos.toList()
    notifyIteRemover(posicion)
    notifyDataSetChanged()

    fun actualizarDatos(nuevoTitulo: String, NumeroTicket: String){
        val objConexion = claseConexion().cadenaConexion()

        val actualizarTicket = objConexion?.prepareStatement("update TabTickets = ? where numeroTicket = ?")!!
        actualizarTicket.setString(1,nuevoTitulo)
        actualizarTicket.setString(2, NumeroTicket)
        actualizarTicket.executeUpdate()
    }

    override fun  onCreateViewHolder(parent : ViewGroup,viewType: Int): ViewHolder{
        val vista =
            LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false)
        return ViewHolder(vista)
    }


    override fun getItemCount() = Datos.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        val ticket = Datos[position]
        holder.textView.text = ticket.TituloTicket

        holder.imgBorrar.setOnClickListener {
            val context = holder.itemView.context

            val builder = AlertDialog.Builder(context)
            builder.setTitle("Eliminar")
            builder.setMessage("¿Esta seguro de eliminar el ticket?")

            builder.setPositiveButton("Si"){ dialog, which ->
                eliminarDatos(ticket.TituloTicket,position)

            }

            builder.setNegativeButton("No"){dialog,which ->
                dialog.dismiss()
            }

            val dialog = builder.create()
            dialog.show()
        }

        holder.imgEditar.setOnClickListener {
            val context = holder.itemView.context

            val builder = AlertDialog.Builder(context)
            cuadroTexto.setHint(ticket.TituloTicket)
            builder.setView(cuadroTexto)

            builder.setPositiveButton("Actualizar"){dialog, which ->
                actualizarDatos(cuadroTexto.text.toString(), ticket.NumeroTicket)

            }

            builder.setNegativeButton("Cancelar"){dialog, which ->
                dialog.dismiss()

                val dialog = builder.create()
                dialog.show()

        }
    }





}