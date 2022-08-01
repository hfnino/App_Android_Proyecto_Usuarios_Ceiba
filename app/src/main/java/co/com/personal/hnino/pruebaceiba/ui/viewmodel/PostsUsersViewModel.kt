package co.com.personal.hnino.pruebaceiba.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.com.personal.hnino.pruebaceiba.data.model.DataTemporalProvider
import co.com.personal.hnino.pruebaceiba.domain.GetListPostUsersUseCase
import co.com.personal.hnino.pruebaceiba.domain.GetListUsersUseCase
import co.com.personal.hnino.pruebaceiba.domain.model.PostUsersModel
import co.com.personal.hnino.pruebaceiba.domain.model.UsersModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel // Con esta etiqueda, preparamos clases de tipo  view Model como sucede en este caso,
// y lo hacemos con el fin de que sea posible inyectarle las dependencias necesarias que van a ser
// gestionadas por Dagger Hilt
class PostsUsersViewModel @Inject constructor(
    private var getListPostsUsersUseCase: GetListPostUsersUseCase,
    private var dataTemporalProvider: DataTemporalProvider
): ViewModel() {
    // Estamos inyectando la dependencia "GetListPostUsersUseCase" asociada a la variable privada
    // getListPostsUsersUseCase. Con lo anterior ya no tenemos que crear una instancia de esa clase,
    // es decir  "var getListUsersUseCase = GetListUsersUseCase()"

    val listPostsUsersMLD = MutableLiveData<List<PostUsersModel>>()

    var isLoading = MutableLiveData<Boolean>() // Esta variable de tipo LiveData Boolean, nos ayuda
    // a mostrar u ocultar la barra circular de progreso cuando el sistema esta conectandose a la API
    // para traer la información del Backend

    fun getListPostsUsers(queryByKeyWords: String) {
        viewModelScope.launch {  // Cuando se usa arquitecturas como MVP, si la activity muere es
            // necesario parar la coorrutina que se encuentre en ejecución para que no salga errores,
            // en este caso como usamos la arquitectura MVVM ese inconveniente no lo tenemos por que
            // podemos usar la coorutina viewModelScope.launch {} que puede controlarse automaticamente
            // El codigo existente dentro de esta corrutina se va a ejecutar en un hilo secundario,
            // es decir en un hilo diferente al principal
            isLoading.postValue(true) // Muestra barra circular de progreso, la variable isLoading
            // que es de tipo LiveData Boolean se da cuenta que le hicimos un cambio y avisara a la
            // activity y/o Fragment correspondiente y este ultimo va a hacer lo que crea conveniente.
            // Si no usaramos Live Data, tendriamos que configurar esta funcion para que retorne el
            // objeto la variable "isLoading" a la vista donde esta funcion fue llamada.
            val respuestaListPostsUsersFromApi = getListPostsUsersUseCase(queryByKeyWords) // no se
            // requiere llamar ningun metodo de clase, ya que se esta usando el operador invoke
            if(respuestaListPostsUsersFromApi != null){

                println(" ============la lista enviada desde PostsUsersViewModel para mostrar en el " +
                        "Recycler View de objetos PostsUsersModel es =====>  " +
                        respuestaListPostsUsersFromApi)

                listPostsUsersMLD.postValue(respuestaListPostsUsersFromApi) // la variable
                // listPostsUsersMLD que es de tipo LiveData se da cuenta que le hicimos un cambio y avisara
                // a la activity y/o Fragment correspondiente y este ultimo va a hacer lo que crea conveniente.
                // Si no usaramos Live Data, tendriamos que configurar esta funcion para que retorne
                // el objeto la variable "respuestaListPostsUsersFromApi" a la vista donde esta funcion fue llamada.

                isLoading.postValue(false) // Oculta barra circular de progreso

            }
            else{
                println(" ==============================> Verificar el caso, ya que la respuesta debio " +
                        "ser una lista de objetos de tipo PostUsersModel, y en su lugar, se obtuvo como" +
                        " respuesta un null, Por favor verifique que tenga acceso a Internet" +
                        " o en su defecto pueda ser fallas en el servidor.") // Prueba de Escritorio

                isLoading.postValue(false)
            }
        }
    }
}