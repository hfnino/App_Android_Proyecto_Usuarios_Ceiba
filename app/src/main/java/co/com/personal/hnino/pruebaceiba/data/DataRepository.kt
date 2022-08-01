package co.com.personal.hnino.pruebaceiba.data

import co.com.personal.hnino.pruebaceiba.data.database.dao.AddressDao
import co.com.personal.hnino.pruebaceiba.data.database.dao.CompanyDao
import co.com.personal.hnino.pruebaceiba.data.database.dao.GeoDao
import co.com.personal.hnino.pruebaceiba.data.database.dao.UsersDao
import co.com.personal.hnino.pruebaceiba.data.database.model.*
import co.com.personal.hnino.pruebaceiba.data.model.entidades.PostsUsersFromApi
import co.com.personal.hnino.pruebaceiba.data.model.entidades.UsersFromApi
import co.com.personal.hnino.pruebaceiba.data.network.PostsUsersService
import co.com.personal.hnino.pruebaceiba.data.network.UsersService
import co.com.personal.hnino.pruebaceiba.domain.model.*
import javax.inject.Inject

class DataRepository @Inject constructor(
    private val apiUsersService: UsersService,
    private val apiPostsUsersService: PostsUsersService,
    private val usersDao: UsersDao, // Inyectamos esta dependencia por medio del modulo RoomModule ya que no se puede inyectar directamente por ser una interfaz
    private val addressDao: AddressDao, // Inyectamos esta dependencia por medio del modulo RoomModule ya que no se puede inyectar directamente por ser una interfaz
    private val geoDao: GeoDao, // Inyectamos esta dependencia por medio del modulo RoomModule ya que no se puede inyectar directamente por ser una interfaz
    private val companyDao: CompanyDao // Inyectamos esta dependencia por medio del modulo RoomModule ya que no se puede inyectar directamente por ser una interfaz
) { // Con @Inject constructor(), estamos preparando esta clase (que no es una activity ni view Models)
    // para que pueda ser inyectada en otras clases y tambien la preparamos para que se le pueda
    // inyectar dependencias de otras clases que van a ser gestionadas por Dagger Hilt

    suspend fun getListaUsersFromApi(queryByKeyWords:String): List<UsersModel>{

        val respuestaListaUsersFromApi: List<UsersFromApi> = apiUsersService.getListUsersRetrofitApiClientInterface(queryByKeyWords)

        val respuestaListaUsers: List<UsersModel> = respuestaListaUsersFromApi.map { it.converterToUserModel() } // La función .map es propia de
        //un List<XXXXX> y es como un bucle tipo For que para este caso nos es util para tomar cada uno de los objetos de tipo UsersFromApi
        // existentes en la lista respuestaListaUsersFromApi y  convertirlos (Maper) a tipo UsersModel, de esta manera, la variable
        // respuestaListaUsers resultara en una nueva lista, pero ahora con objetos de tipo UsersModel

        return  respuestaListaUsers // Retornamos la lista de objetos de tipo UsersModel
    }

    suspend fun getlistUsersFromDataBase(): List<UsersModel>{
        var respuestaListUsersModel: MutableList<UsersModel> = mutableListOf()
        val respuestaListUsersEntity: List<UsersEntity> = usersDao.getAllUsers()

        respuestaListUsersEntity.map {
            val respuestaAddressEntity: AddressEntity = addressDao.getAddress(it.id)
            val respuestaGeoEntity: GeoEntity = geoDao.getGeo(it.id)
            val respuestaCompanyEntity: CompanyEntity = companyDao.getCompany(it.id)

            val respuestaUsersModel: UsersModel = UsersModel(it.id, it.nombre, it.username, it.email,
                respuestaAddressEntity.converterToAddressModel(respuestaGeoEntity), it.phone, it.website,
                respuestaCompanyEntity.converterToCompanyModel()
                )

            respuestaListUsersModel.add(respuestaUsersModel)

        }

        println(" =======================> la lista de objetos de tipo UsersModel extraidas de la base de datos local con " +
                "Room es =>  " + respuestaListUsersModel.toString()) // Prueba de escritorio.

        return  respuestaListUsersModel

    }

    suspend fun insertListUsersEntityToDataBase(listUsersModel: List<UsersModel>){

        val listUsersEntity: List<UsersEntity> = listUsersModel.map {
            it.converterTiUsersEntity()
        }

        val listAddressEntity: List<AddressEntity> = listUsersModel.map {
            it.address.converterToAddressEntity(it.id)
        }

        val listGeoEntity: List<GeoEntity> = listUsersModel.map {
            it.address.geo.converterToGeoEntity(it.id)
        }

        val listCompanyEntity: List<CompanyEntity> = listUsersModel.map {
            it.company.converterToCompanyEntity(it.id)
        }

        usersDao.insertAllUsers(listUsersEntity)
        addressDao.insertAllAddress(listAddressEntity)
        geoDao.insertAllGeo(listGeoEntity)
        companyDao.insertAllCompany(listCompanyEntity)
    }

    suspend fun getListaPostsUsersFromApi(queryByKeyWords:String): List<PostUsersModel>{

        val respuestaListaPostsUsersFromApi: List<PostsUsersFromApi> =
            apiPostsUsersService.getListPostsUsersRetrofitApiClientInterface(queryByKeyWords)

        val respuestaListaPostsUsers: List<PostUsersModel> =
            respuestaListaPostsUsersFromApi.map { it.converterToPostsUserModel() }
        // La función .map es propia de un List<XXXXX> y es como un bucle tipo For que para este
        // caso nos es util para tomar cada uno de los objetos de tipo PostsUsersFromApi
        // existentes en la lista respuestaListaPostsUsersFromApi y  convertirlos (Maper) a tipo
        // PostUsersModel, de esta manera, la variable respuestaListaPostsUsers resultara en una
        // nueva lista, pero ahora con objetos de tipo PostUsersModel

        return  respuestaListaPostsUsers // Retornamos la lista de objetos de tipo PostUsersModel
    }

    suspend fun deleteAllFromDataBase(){
        companyDao.deleteAllCompany()
        geoDao.deleteAllGeo()
        addressDao.deleteAllAddress()
        usersDao.deleteAllUsers()
    }
}