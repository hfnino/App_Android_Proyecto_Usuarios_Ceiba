package co.com.personal.hnino.pruebaceiba.data.database.dao

import androidx.room.*
import co.com.personal.hnino.pruebaceiba.data.database.model.UsersEntity

@Dao //Con esta anotación le decimos a Room que esta clase es una DAO (Data Access Objet)
interface UsersDao {

    @Query("SELECT * FROM tbl_Users ORDER BY id ASC") // Consulta para obtener todos los usuaruis de la BD local con Room
    suspend fun getAllUsers():List<UsersEntity>//Se usa suspend por que vamos a trabajar con las corrutinas.
    //por otro lado, usamos UsersEntity por que es una entidad, si usaramos la clase UsersModel no nos funciona

    @Query("SELECT * FROM tbl_Users WHERE id =:id ") // Consulta a la base de datos para obtener el usuario
    // que tenga el id correspondiente
    suspend fun getUser(id: Int): UsersEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE) // Si se intentara insertar un objeto de tipo UsersEntity con un id (PrimaryKey)
    //ya existente en la base de datos entonces no sale un error sino que el registro se reemplaza por el nuevo, para lo anterior
    // usamos "onConflict = OnConflictStrategy.REPLACE"
    suspend fun insertUser(usersEntity : UsersEntity) //Se usa suspend por que vamos a trabajar con las corrutinas
    // esta función insertara un solo objeto de tipo UsersEntity
    //por otro lado, usamos UsersEntity por que es una entidad, si usaramos la clase UsersModel no nos funciona

    @Insert(onConflict = OnConflictStrategy.REPLACE ) // Si se intentara insertar un objeto de tipo UsersEntity con un id (PrimaryKey)
    //ya existente en la base de datos entonces no sale un error sino que el registro se reemplaza por el nuevo, para lo anterior
    // usamos "onConflict = OnConflictStrategy.REPLACE"
    suspend fun insertAllUsers(listUsersEntity : List<UsersEntity>) //Se usa suspend por que vamos a trabajar con las corrutinas
    // esta función insertará una lista de objetos de tipo UsersEntity
    //por otro lado, usamos UsersEntity por que es una entidad, si usaramos la clase UsersModel no nos funciona

    @Update
    suspend fun updateUser(usersEntity : UsersEntity) // Room es los suficientemente inteligente para saber
    // que el id de usersEntity recibido es la Primary Key de la base de datos, asi que buscara por esa primary key
    // y actualizara el registro.

    @Query("DELETE FROM tbl_Users WHERE id =:id") // Elimina los registros existentes en esa tabla de la BD local
    // y donde la condicion se cumpla
    suspend fun deleteUser(id: Int) // Si en lugar de recibir un Int, recibieramos un objeto de tipo UsersEntity,
    // Room es los suficientemente inteligente para saber que el id del objeto UsersEntity es la Primary Key de la base de datos,
    // asi que buscara por esa primary key y eliminara ese registro, claro esta que para que lo anterior fucione,
    // debemos usar la anotción @Delete y no @Query

    @Query("DELETE FROM tbl_Users") // Elimina todos los registros existente em la esa tabla de la BD local con Room
    suspend fun deleteAllUsers() //Se usa suspend por que vamos a trabajar con las corrutinas.
}