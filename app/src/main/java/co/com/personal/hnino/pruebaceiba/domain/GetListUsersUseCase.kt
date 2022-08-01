package co.com.personal.hnino.pruebaceiba.domain

import co.com.personal.hnino.pruebaceiba.data.DataRepository
import co.com.personal.hnino.pruebaceiba.domain.model.UsersModel
import javax.inject.Inject

class GetListUsersUseCase @Inject constructor( private var repository: DataRepository){
    // Esta clase se creo con el objetivo de representar un caso de uso, Con @Inject constructor(),
    // estamos preparando esta clase (que no es una activity ni view Models) para que pueda ser inyectada
    // en otras clases y tambien la preparamos para que se le pueda inyectar dependencias de otras clases
    // que van a ser gestionadas por Dagger Hilt

    suspend operator fun invoke(queryByKeyWords:String): List<UsersModel>?{

        var listaUsersModelFromDatabase: List<UsersModel> = repository.getlistUsersFromDataBase()

        if (listaUsersModelFromDatabase.isNullOrEmpty()){
            val listaUsersModel: List<UsersModel> = repository.getListaUsersFromApi(queryByKeyWords)

            repository.insertListUsersEntityToDataBase(listaUsersModel)//guardamos la data  de los usuarios
            // capturada de la Api y la guardamos en la Base de datos

            listaUsersModelFromDatabase = repository.getlistUsersFromDataBase() // como ya la data de usuario
            // existe en la base de datos, entonces procedemos a obtener los datos existentes de la
            // base de datos.
        }

        return listaUsersModelFromDatabase
    }
    // el operador de funcion invoke, nos permite llamar a esta funcion automaticamente sin nececidad
    // de llamar a ningún metodo de esta clase. por ejemplo, si en cualquier otra clase creamos una
    // instancia de esta clase, es decir por ejemplo "var xyz = GetListUsersUseCase()", y luego
    // quiero llamar a la funcion "invoke", facilmente lo podemos hacer asi =>   xyz() ----- como vemos
    // la funcion invoke se llama automaticamente sin necesidad de llamar a ningun metodo.
    // Esta función nos retorna el listado de objetos de tipo UsersModel en JSON obtenidas desde la API
}