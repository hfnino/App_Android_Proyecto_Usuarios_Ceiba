package co.com.personal.hnino.pruebaceiba.data.model.entidades

import com.google.gson.annotations.SerializedName

//Este es el modelo de datos que usaremos para trabajar con Retrofit para capturar toda la información
// asociada a las publicaciones de los usuarios por medio de la API

data class PostsUsersFromApi(
    @SerializedName("id") val id: Int,
    @SerializedName("userId") val userId: Int,
    @SerializedName("title") val title: String,
    @SerializedName("body") val body: String
)

// desde la API se recibe el por ejemplo el title pero el dato lo almacenamos en la variable 'titulo',
// simplemente cambiamos el nombre para que la aplicación sea mas facil de entender si asi fuera necesario,
// en este caso no era necesario, pero se implemento para tener un ejemplo