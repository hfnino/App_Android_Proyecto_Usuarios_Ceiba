package co.com.personal.hnino.pruebaceiba.data.database.dao

import androidx.room.*
import co.com.personal.hnino.pruebaceiba.data.database.model.AddressEntity
import co.com.personal.hnino.pruebaceiba.data.database.model.UsersEntity

@Dao //Con esta anotación le decimos a Room que esta clase es una DAO (Data Access Objet)
interface AddressDao {

    @Query("SELECT * FROM tbl_Address ORDER BY user_id ASC") // Consulta para obtener todos las Address de la BD local con Room
    suspend fun getAllAddress():List<AddressEntity>//Se usa suspend por que vamos a trabajar con las corrutinas.
    //por otro lado, usamos AddressEntity por que es una entidad, si usaramos la clase AddressModel no nos funciona

    @Query("SELECT * FROM tbl_Address WHERE user_id=:user_id ") // Consulta a la base de datos para obtener la Address
    // que tenga el id de usario correspondiente
    suspend fun getAddress(user_id: Int): AddressEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE) // Si se intentara insertar un objeto de tipo AddressEntity con un id (PrimaryKey)
    //ya existente en la base de datos entonces no sale un error sino que el registro se reemplaza por el nuevo, para lo anterior
    // usamos "onConflict = OnConflictStrategy.REPLACE"
    suspend fun insertAddress(addressEntity : AddressEntity) //Se usa suspend por que vamos a trabajar con las corrutinas
    // esta función insertara un solo objeto de tipo AddressEntity
    //por otro lado, usamos AddressEntity por que es una entidad, si usaramos la clase UsersModel no nos funciona

    @Insert(onConflict = OnConflictStrategy.REPLACE ) // Si se intentara insertar un objeto de tipo AddressEntity con un id (PrimaryKey)
    //ya existente en la base de datos entonces no sale un error sino que el registro se reemplaza por el nuevo, para lo anterior
    // usamos "onConflict = OnConflictStrategy.REPLACE"
    suspend fun insertAllAddress(listAddressEntity : List<AddressEntity>) //Se usa suspend por que vamos a trabajar con las corrutinas
    // esta función insertará una lista de objetos de tipo AddressEntity
    //por otro lado, usamos AddressEntity por que es una entidad, si usaramos la clase UsersModel no nos funciona

    @Update
    suspend fun updateAddress(addressEntity : AddressEntity) // Room es los suficientemente inteligente para saber
    // que el id de addressEntity recibido es la Primary Key de la base de datos, asi que buscara por esa primary key
    // y actualizara el registro.

    @Query("DELETE FROM tbl_Address WHERE user_id =:user_id") // Elimina los registros existentes en esa tabla de la BD local
    // y donde la condicion se cumpla
    suspend fun deleteAddress(user_id: Int) // Si en lugar de recibir un Int, recibieramos un objeto de tipo AddressEntity,
    // Room es los suficientemente inteligente para saber que el id del objeto AddressEntity es la Primary Key de la base de datos,
    // asi que buscara por esa primary key y eliminara ese registro, claro esta que para que lo anterior fucione,
    // debemos usar la anotción @Delete y no @Query

    @Query("DELETE FROM tbl_Address") // Elimina todos los registros existente em la esa tabla de la BD local con Room
    suspend fun deleteAllAddress() //Se usa suspend por que vamos a trabajar con las corrutinas.
}