package co.com.personal.hnino.pruebaceiba.ui.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.com.personal.hnino.pruebaceiba.R
import co.com.personal.hnino.pruebaceiba.domain.model.PostUsersModel

class AdapterRecyclerViewPostsUsers(private var listItemsPostsUserModel: List<PostUsersModel>):
    RecyclerView.Adapter<ViewHolderPostsUsers>(){
    //Esta clase Adapter, extiende de RecyclerView.Adapter, por lo que es necesario implementar los
    // 3 metodos abtractos siguientes y estos metodos nos permiten usar la data el listado de objetos
    // de tipo PostsUsersModel que recibimos como argumentos de AdapterRecyclerViewPostsUsers para
    // luego mostrar toda esa info en el RecyclerView haciendo uso del ViewHolder que
    // llamamos ViewHolderPostsUsers

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderPostsUsers {
        //Esta función retorna un objeto de tipo ViewwHolder "ViewHolderPostsUsers. La data de los objetos
        // contenidos en el array listItemsPostsUserModel es usada por el ViewHolder "ViewHolderPostsUsers"
        // para mostrar al usuario la información de cada objeto haciendo uso del
        // layout que creamos llamado activity_list_posts_users_item_rview

        val layoutInflater = LayoutInflater.from(parent.context) // Cuanto no estamos en una Activity, podemos
        // capturar el contexto por medio de cualquiera de las vistas las cuales tenemos a dispoción a traves
        // del argumento recibido llamado "parent", que es de tipo ViewGroup que es una vista de Android, por
        // tal motivo es que  ponemos parent.context
        return ViewHolderPostsUsers(layoutInflater.inflate(R.layout.activity_list_posts_users_item_rview,
            parent, false))
    }

    override fun onBindViewHolder(holderPostsUser: ViewHolderPostsUsers, position: Int) {
        //Este metodo tiene como función asociar los datos dinamicos del objeto correspondiente a los elementos de la
     // vista layout activity_list_users_item_rview.xml y esos datos son los que se van a mostrar al usuario.

        println("=====================> El titulo del post es  => " + listItemsPostsUserModel.get(position).title) // Prueba de Escritorio

        var item = listItemsPostsUserModel.get(position)
        holderPostsUser.render(item) //Usamos la funcion render que creamos en la clase ViewHolderUsers
                    // y le enviamos como parametro el item que es el objeto existente en la posisión correspondiente y el
                    // onClickListener que recibimos como argumento al principio de esta misma clase

    }

    override fun getItemCount(): Int {
        println("===================> La lista contiene " + listItemsPostsUserModel.size + " para mostrar") // Prueba de Escritorio
        return listItemsPostsUserModel.size //retornal el tamaño del array y este numero es el que usa el sistema para mostrar
        //la cantidad de elementos en el recyclerView que visualizara el usuario
    }
}