package co.com.personal.hnino.pruebaceiba.ui.view.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import co.com.personal.hnino.pruebaceiba.databinding.ActivityListPostsUsersItemRviewBinding
import co.com.personal.hnino.pruebaceiba.domain.model.PostUsersModel

class ViewHolderPostsUsers(view:View):RecyclerView.ViewHolder(view) {
    //El ViewHolder "ViewHolderPostsUsers" nos ayuda a mostrar al usuario la información de cada objeto
    // haciendo uso del layout que creamos llamado activity_list_posts_users_item_rview

    private val binding = ActivityListPostsUsersItemRviewBinding.bind(view) //implementamos el View Bindind
    // donde ActivityListPostsUsersItemRviewBinding es el nombre que relaciona directamente al layout
    // activity_list_posts_users_item_rview

    fun render (itemPostUserModel: PostUsersModel){

        binding.tvPostTile.text = itemPostUserModel.title
        binding.tvPostBody.text = itemPostUserModel.body

    }

}