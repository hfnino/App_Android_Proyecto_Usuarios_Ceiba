package co.com.personal.hnino.pruebaceiba.data.network

import co.com.personal.hnino.pruebaceiba.data.model.entidades.UsersFromApi
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface UsersApiClientInterface { // Al ser una interfaz, no se le puede poner un @Inject constructor() para que se pueda
    //inyectar en otras clases. para resolver este problema se creo el modulo NetworkModuleProvider donde se creo la función
    // provideUsuariosApiClient que con ayuda de la etiqueta @Providers retorna un objeto de tipo UsuariosApiClientInterface
    // significa que si en cualquier clase necesitamos una instacia de interfaz UsuariosApiClientInterface, simplemente la
    // inyectamos, y Dagger Hilt va a buscar y a encontrar a esta funcion, que retorna un objeto de UsuariosApiClientInterface
    // y la va a proveer.

    //En esta interface, creamos los metodos abstractos que necesitamos para consumir los servicios API requeridos

    @GET
    suspend fun getApiUsuarios(@Url url:String): Response<List<UsersFromApi>> // este metodo nos permite acceder
    // al servicio de nuestra API por medio de peticiones GET y debe ser de tipo suspend ya que se usa en una
    // corrutina (hilo diferente al principal)
}