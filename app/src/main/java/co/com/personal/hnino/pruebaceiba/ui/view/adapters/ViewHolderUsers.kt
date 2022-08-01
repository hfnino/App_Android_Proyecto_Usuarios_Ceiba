package co.com.personal.hnino.pruebaceiba.ui.view.adapters

import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import co.com.personal.hnino.pruebaceiba.R
import co.com.personal.hnino.pruebaceiba.databinding.ActivityListUsersBinding
import co.com.personal.hnino.pruebaceiba.databinding.ActivityListUsersItemRviewBinding
import co.com.personal.hnino.pruebaceiba.domain.model.UsersModel


class ViewHolderUsers(view:View):RecyclerView.ViewHolder(view) {
    //El ViewHolder "ViewHolderUsers" nos ayuda a mostrar al usuario la información de cada objeto haciendo uso del
    // layout que creamos llamado activity_list_users_item_rview

    private val binding = ActivityListUsersItemRviewBinding.bind(view) //implementamos el View Bindind donde
    // ActivityListUsersItemRviewBinding es el nombre que relaciona directamente al layout o activity_list_users_item_rview.xml

    fun render (itemUserModel: UsersModel, onClickListenerUsersModel: (UsersModel) -> Unit){

        binding.tvNombreUser.text = itemUserModel.nombre
        binding.tvNumTelefonico.text = itemUserModel.phone
        binding.tvEmail.text = itemUserModel.email

        /*
        itemView.setOnClickListener { //Escucha de los click cuando se hace click en cualquier parte de la vista
            onClickListenerUsersModel(itemUserModel) // Retorna el objeto seleccionado y lo transporta hacia el
                // fichero ListUsersActivity.kt a la funcion itemSeleccionado ( ---------- )
        }
        */

        binding.tvVerPublic.setOnClickListener{

            onClickListenerUsersModel(itemUserModel) // Retorna el objeto seleccionado y lo transporta hacia el
            // fichero ListUsersActivity.kt a la funcion itemSeleccionado ( ---------- )
        }

    }

    private fun onClickListenerUsersModel(itemUserModel: UsersModel) {
    }
}