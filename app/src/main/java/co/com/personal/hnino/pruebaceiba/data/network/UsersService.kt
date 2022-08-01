package co.com.personal.hnino.pruebaceiba.data.network

import co.com.personal.hnino.pruebaceiba.data.model.entidades.UsersFromApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UsersService @Inject constructor(private val apiUsers: UsersApiClientInterface) { // Con @Inject constructor(),
    // estamos preparando  esta clase (que no es una activity ni view Models) para que pueda ser inyectada en otras clases y tambien la
    // preparamos para que se le pueda inyectar dependencias de otras clases que van a ser gestionadas por Dagger Hilt.
    // ------------ Con apiUsers: UsersApiClientInterface  estamos inyectando una dependencia de la interface UsersApiClientInterface,
    // y al ser una interface, Dagger hilt va a saber que no es una dependencia de las faciles de proveer y va a ir a buscar automaticamente una
    // funcion que este en algun modulo que tenga la etiqueta @Providers y que provea un objeto de la interface UsersApiClientInterface y
    // dicha funcion la va a encontrar en el modulo que creamos llamado NetworkModuleProvider y esa funcion es la que llamamos
    // provideUsersApiClient y que nos provee un objeto de la interfaz UsersApiClientInterface el cual ya nos retorna un retrofit
    // mesclado con la interfaz,es decir ya viene el retrofit completo, por lo que podemos usar la variable apiUsers en lugar de
    // retrofit.create(UsersApiClientInterface::class.java).

    suspend fun getListUsersRetrofitApiClientInterface(queryByKeyWords:String): List<UsersFromApi> { // Se usa suspend por que se esta trabajando
        // con corrutinas

        return withContext(Dispatchers.IO) {
            // El codigo existente dentro de esta corrutina se va a ejecutar en un hilo secundario, es decir en un
            //hilo diferente al principal y retorna el resultado de tipo List<UsersFromApi>

            val respuesta = apiUsers.getApiUsuarios("$queryByKeyWords")//La variable
            //respuesta es la respuesta de tipo Response<List<UsersFromApi>> que obtenemos de la API en Json.
            // el String contenido en queryByKeyWords" se lo agregamos al final de la URL base que configuramos
            // en la función NetworkModuleProvider.provideUsersRetrofit() en
            // .baseUrl("https://jsonplaceholder.typicode.com/")------------- apiUsers que es de tipo interface
            // UsersApiClientInterface (se inyecto esta dependencia), al ser una interface, Dagger hilt va a saber
            // que no es una dependencia de las faciles de proveer y va a ir a buscar automaticamente
            // una funcion que este en algun modulo que tenga la etiqueta @Providers y que provea un objeto de la interface
            // UsersApiClientInterface y dicha funcion la va a encontrar en el modulo que creamos llamado NetworkModuleProvider
            // y esa funcion es la que llamamos provideUsersApiClient y que nos provee un objeto de la interfaz UsersApiClientInterface
            // el cual ya nos retorna un retrofit mesclado con la interfaz,es decir ya viene el retrofit completo,
            //  por lo que podemos usar la variable apiUsers en lugar de retrofit.create(UsersApiClientInterface::class.java).

            val respuestaUsersFromApi = respuesta.body()
            val respuestaUsersFromApiAux = respuestaUsersFromApi ?: emptyList<UsersFromApi>()  // emptyList aplica Si la petición
            // falla, retornara una lista de objetos de tipo UsersFromApi pero vacia.

            respuestaUsersFromApiAux
        }
    }


}