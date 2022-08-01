package co.com.personal.hnino.pruebaceiba.data.network;

import co.com.personal.hnino.pruebaceiba.data.model.entidades.PostsUsersFromApi
import co.com.personal.hnino.pruebaceiba.data.model.entidades.UsersFromApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class PostsUsersService @Inject constructor (private val apiPostsUsers: PostsUsersApiClientInterface){
    // Con @Inject constructor(), estamos preparando  esta clase (que no es una activity ni view Models)
    // para que pueda ser inyectada en otras clases y tambien la preparamos para que se le pueda inyectar
    // dependencias de otras clases que van a ser gestionadas por Dagger Hilt.
    // Con apiPostsUsers: PostsUsersApiClientInterface estamos inyectando una dependencia de la interface
    // PostsUsersApiClientInterface, y al ser una interface, Dagger hilt va a saber que no es una dependencia
    // de las faciles de proveer y va a ir a buscar automaticamente una funcion que este en algun modulo que
    // tenga la etiqueta @Providers y que provea un objeto de la interface PostsUsersApiClientInterface y
    // dicha funcion la va a encontrar en el modulo que creamos llamado NetworkModuleProvider y esa funcion
    // es la que llamamos providePostsUsersApiClient y que nos provee un objeto de la interfaz
    // PostsUsersApiClientInterface el cual ya nos retorna un retrofit mesclado con la interfaz,es decir ya
    // viene el retrofit completo, por lo que podemos usar la variable apiPostsUsers en lugar de
    // retrofit.create(UsersApiClientInterface::class.java).

    suspend fun getListPostsUsersRetrofitApiClientInterface(queryByKeyWords:String): List<PostsUsersFromApi> {
        // Se usa suspend por que se esta trabajando con corrutinas

        return withContext(Dispatchers.IO) {
            // El codigo existente dentro de esta corrutina se va a ejecutar en un hilo secundario, es decir en un
            //hilo diferente al principal y retorna el resultado de tipo List<PostsUsersFromApi>

            val respuesta = apiPostsUsers.getApiPostsUsers("$queryByKeyWords")//La variable
            //respuesta es la respuesta de tipo Response<List<PostsUsersFromApi>> que obtenemos de la API en Json.
            // el String contenido en queryByKeyWords" se lo agregamos al final de la URL base que configuramos
            // en la función NetworkModuleProvider.provideUsersRetrofit() en
            // .baseUrl("https://jsonplaceholder.typicode.com/")------------- apiPostsUsers que es de tipo interface
            // PostsUsersApiClientInterface (se inyecto esta dependencia), al ser una interface, Dagger hilt va a saber
            // que no es una dependencia de las faciles de proveer y va a ir a buscar automaticamente
            // una funcion que este en algun modulo que tenga la etiqueta @Providers y que provea un objeto de la interface
            // PostsUsersApiClientInterface y dicha funcion la va a encontrar en el modulo que creamos llamado NetworkModuleProvider
            // y esa funcion es la que llamamos providePostsUsersApiClient y que nos provee un objeto de la interfaz
            // PostsUsersApiClientInterface el cual ya nos retorna un retrofit mesclado con la interfaz,es decir ya viene
            // el retrofit completo, por lo que podemos usar la variable apiPostsUsers en lugar de
            // retrofit.create(PostsUsersApiClientInterface::class.java).

            val respuestaPostsUsersFromApi = respuesta.body()
            val respuestaPostsUsersFromApiAux = respuestaPostsUsersFromApi
                ?: emptyList<PostsUsersFromApi>()  // emptyList aplica Si la petición
            // falla, retornara una lista de objetos de tipo UsersFromApi pero vacia.

            respuestaPostsUsersFromApiAux
        }
    }
}
