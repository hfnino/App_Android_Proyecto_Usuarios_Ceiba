package co.com.personal.hnino.pruebaceiba.ui.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.com.personal.hnino.pruebaceiba.R
import co.com.personal.hnino.pruebaceiba.domain.model.UsersModel

class AdapterRecyclerViewUsers(private var listItemsUserModel: List<UsersModel>,
                               private val onClickListenerItemUserModel: (UsersModel) -> Unit) : RecyclerView.Adapter<ViewHolderUsers>(){
    //Esta clase Adapter, extiende de RecyclerView.Adapter, por lo que es necesario implementar los 3 metodos abtractos siguientes y
    // estos metodos nos permiten usar la data el listado de objetos de tipo UsersModel que recibimos como argumentos
    // de AdapterRecyclerViewUsers para luego mostrar toda esa info en el RecyclerView haciendo uso del ViewHolder que
    // llamamos ViewHolderUsers

    //private val onClickListener:(ImgsApolo) -> Unit ------ Es una función lamda que la usamos para enviarla a al ViewHolder y ImgsApolo
    // es el tipo de objeto que queremos transportar

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderUsers {
        //Esta función retorna un objeto de tipo ViewwHolder "ViewHolderUsers" la data de los objetos contenidos en el array listItemsUserModel
        // para que luego el ViewHolder "ViewHolderUsers" muestre al usuario la información de cada objeto haciendo uso del
        // layout que creamos llamado activity_list_users_item_rview

        val layoutInflater = LayoutInflater.from(parent.context) // Cuanto no estamos en una Activity, podemos capturar el contexto
        // por medio de cualquiera de las vistas las cuales tenemos a dispoción a traves del argumento recibido llamado "parent",
        // que es de tipo ViewGroup que es una vista de Android, por tal motivo es que  ponemos parent.context
        return ViewHolderUsers(layoutInflater.inflate(R.layout.activity_list_users_item_rview, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolderUsers, position: Int) {
        //Este metodo tiene como función asociar los datos dinamicos del objeto correspondiente a los elementos de la
     // vista layout activity_list_users_item_rview.xml y esos datos son los que se van a mostrar al usuario.

        println("=====================> El nombre de usuario es => " + listItemsUserModel.get(position).nombre) // Prueba de Escritorio

        var item = listItemsUserModel.get(position)
        holder.render(item, onClickListenerItemUserModel) //Usamos la funcion render que creamos en la clase ViewHolderUsers
                    // y le enviamos como parametro el item que es el objeto existente en la posisión correspondiente y el
                    // onClickListener que recibimos como argumento al principio de esta misma clase

    }

    override fun getItemCount(): Int {
        println("===================> La lista contiene " + listItemsUserModel.size + " para mostrar") // Prueba de Escritorio
        return listItemsUserModel.size //retornal el tamaño del array y este numero es el que usa el sistema para mostrar
        //la cantidad de elementos en el recyclerView que visualizara el usuario
    }
}