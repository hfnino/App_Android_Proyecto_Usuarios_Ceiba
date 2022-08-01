package co.com.personal.hnino.pruebaceiba.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.com.personal.hnino.pruebaceiba.data.model.DataTemporalProvider
import co.com.personal.hnino.pruebaceiba.domain.DeleteAllFromDataBaseUseCase
import co.com.personal.hnino.pruebaceiba.domain.GetListUsersUseCase
import co.com.personal.hnino.pruebaceiba.domain.model.UsersModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel // Con esta etiqueda, preparamos clases de tipo  view Model como sucede en este caso,
// y lo hacemos con el fin de que sea posible inyectarle las dependencias necesarias que van a ser
// gestionadas por Dagger Hilt

class UsersViewModel @Inject constructor(
    private var getListUsersUseCase: GetListUsersUseCase,
    private var dataTemporalProvider: DataTemporalProvider,
    private var deleteAllFromDataBaseUseCase: DeleteAllFromDataBaseUseCase
): ViewModel() {
    // Estamos inyectando la dependencia "GetListUsersUseCase" asociada a la variable privada getListUsersUseCase.
    // Con lo anterior ya no tenemos que crear una instancia de esa clase, es decir
    // "var getListUsersUseCase = GetListUsersUseCase()"

    val listUsersMLD = MutableLiveData<List<UsersModel>>()
    val UserSelectMLD = MutableLiveData<UsersModel>()
    var isLoading = MutableLiveData<Boolean>() // Esta variable de tipo LiveData Boolean, nos ayuda
    // a mostrar u ocultar la barra circular de progreso cuando el sistema esta conectandose a la API
    // para traer la información del Backend

    fun getListUsers(queryByKeyWords: String) {
        viewModelScope.launch {  // Cuando se usa arquitecturas como MVP, si la activity muere es necesario parar la coorrutina que se encuentre
            // en ejecución para que no salga errores, en este caso como usamos la arquitectura MVVM ese inconveniente no lo tenemos por que podemos
            // usar la coorutina viewModelScope.launch {} que puede controlarse automaticamente ya que es una corrutina pero esta adaptada
            // al ciclo de vida del view Model
            // El codigo existente dentro de esta corrutina se va a ejecutar en un hilo secundario, es decir en un
            //hilo diferente al principal
            isLoading.postValue(true) // Muestra barra circular de progreso ------ la variable isLoading que es de tipo LiveData Boolean
            // se da cuenta que le hicimos un cambio y avisara a la activity y/o Fragment correspondiente y este ultimo va a hacer lo que crea conveniente.
            // Si no usaramos Live Data, tendriamos que configurar esta funcion para que retorne el objeto la variable "isLoading" a la vista donde esta
            // funcion fue llamada.
            val respuestaListUsersFromDataBase = getListUsersUseCase(queryByKeyWords) // no se requiere llamar ningun metodo de clase, ya que se esta usando
            // el operador invoke
            if(respuestaListUsersFromDataBase != null){

                println(" ============la lista enviada desde UsersViewModel para mostrar en el Recycler View de objetos UsersModel es =====>  " +
                        respuestaListUsersFromDataBase)

                Thread.sleep(3000) // para hacer visible la pantalla de loading un poco más

                listUsersMLD.postValue(respuestaListUsersFromDataBase) // la variable
                // listUsersMLD que es de tipo LiveData se da cuenta que le hicimos un cambio y avisara
                // a la activity y/o Fragment correspondiente y este ultimo va a hacer lo que crea conveniente.
                // Si no usaramos Live Data, tendriamos que configurar esta funcion para que retorne
                // el objeto la variable "respuestaListUsersFromApi" a la vista donde esta funcion fue llamada.


                isLoading.postValue(false) // Oculta barra circular de progreso

            }
            else{
                println(" ==============================> Verificar el caso, ya que la respuesta debio ser una lista de objetos de tipo" +
                        " UsersModel, y en su lugar, se obtuvo como respuesta un null, Por favor verifique que tenga acceso a Internet" +
                        " o en su defecto pueda ser fallas en el servidor.") // Prueba de Escritorio

                isLoading.postValue(false)
            }
        }
    }

    fun getItemUserModelSelect(){ // cuendo se usa esta función, se obtiene un un objeto de tipo UserModel
        val itemUserModel: UsersModel = dataTemporalProvider.getItemUserMoldel()
        UserSelectMLD.postValue(itemUserModel) // la variable UserSelectMLD que es de tipo
        //LiveData se da cuenta que le hicimos un cambio y avisara a la activity y/o Fragment correspondiente y este
        //ultimo va a hacer lo que crea conveniente. Si no usaramos Live Data, tendriamos que configurar esta funcion
        // para que retorne el objeto DataTemporalProvider.getItemDatosImagen() a la vista donde esta funcion fue llamada.
    }

    fun setItemUserModelSelect(itemUserMOdelToSave: UsersModel){
        dataTemporalProvider.setItemUserModel(itemUserMOdelToSave)
    }

    fun deleteAllFromDataBase(){
        viewModelScope.launch {
            isLoading.postValue(true) // Muestra barra circular de progreso
            deleteAllFromDataBaseUseCase()
            isLoading.postValue(false) // Oculta barra circular de progreso
        }
    }

}