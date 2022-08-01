package co.com.personal.hnino.pruebaceiba.data.model

import co.com.personal.hnino.pruebaceiba.domain.model.UsersModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton // Usamos el patron de diseño Singleton para mantener una unica instancia de la clase DataTemporalProvider, de tal
// manera que no se creen varias cada vez que llamemos a esta clase. Lo anterior para evitar consumir recursos de memoria sin tener
// la necesitad. Ademas nos ayuda a evitar errores ya que en la clase DataRepository y en la UsersViewModel
// usamos inyección de dependencias por lo que estamos creando 2 instancias diferentes de la clase DataTemporalProvider,
// una tendria la información que nencesitamos y la otra no tendria nada. al usar el patron de diseño Singleton, evitamos
// este problema

class DataTemporalProvider @Inject constructor() {

    private lateinit var itemUserModel: UsersModel

    fun setItemUserModel(itemUserModelAux: UsersModel){
        itemUserModel = itemUserModelAux
    }

    fun getItemUserMoldel(): UsersModel{
        return itemUserModel
    }


}