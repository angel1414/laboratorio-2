package RecyclerViewHelpers

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.acividad_crud_angel_hernandez.R

class ViewHolder (view:View):RecyclerView.ViewHolder(view){
    val txtView: TextView = view.findViewById(R.id.txtTicketCard)
    val imgEditar:ImageView = view.findViewById(R.id.imgEditar)
    val imgBorrar:ImageView = view.findViewById(R.id.imgBorrar)

}
