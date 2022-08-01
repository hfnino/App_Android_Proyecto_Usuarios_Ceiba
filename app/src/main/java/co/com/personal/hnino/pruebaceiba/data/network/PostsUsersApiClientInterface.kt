package co.com.personal.hnino.pruebaceiba.data.network

import co.com.personal.hnino.pruebaceiba.data.model.entidades.PostsUsersFromApi
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface PostsUsersApiClientInterface { // Al ser una interfaz, no se le puede poner un @Inject constructor() para que se pueda
    //inyectar en otras clases. para resolver este problema se creo el modulo NetworkModuleProvider donde se creo la función
    // providePostsUsersApiClient que con ayuda de la etiqueta @Providers retorna un objeto de tipo PostsUsersApiClientInterface
    // significa que si en cualquier clase necesitamos una instacia de interfaz PostsUsersApiClientInterface, simplemente la
    // inyectamos, y Dagger Hilt va a buscar y a encontrar a esta funcion, que retorna un objeto de PostsUsersApiClientInterface
    // y la va a proveer.

    //En esta interface, creamos los metodos abstractos que necesitamos para consumir los servicios API requeridos

    @GET
    suspend fun getApiPostsUsers(@Url url:String): Response<List<PostsUsersFromApi>> // este metodo nos permite acceder
    // al servicio de nuestra API por medio de peticiones GET y debe ser de tipo suspend ya que se usa en una
    // corrutina (hilo diferente al principal)
}
