package co.com.personal.hnino.pruebaceiba.domain.model

import co.com.personal.hnino.pruebaceiba.data.model.entidades.PostsUsersFromApi

//Este es el modelo de datos final con el que la UI y dominio van a trabajar, de tal forma que si hay que
// cambiar el modelo de datos la base de datos, o de Retrifit no va a pasar nada ya que son modelos de datos
// independientes y la información que llegue al dominio va a ser siempre este modelo de datos

data class PostUsersModel (
    val id: Int,
    val userId: Int,
    val title: String,
    val body: String
)

fun PostsUsersFromApi.converterToPostsUserModel(): PostUsersModel{
    var postsUsersModel: PostUsersModel = PostUsersModel(id=id, userId = userId, title = title,
    body = body)
    return postsUsersModel
}
