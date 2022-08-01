package co.com.personal.hnino.pruebaceiba.data.database.dao

import androidx.room.*
import co.com.personal.hnino.pruebaceiba.data.database.model.GeoEntity

@Dao //Con esta anotación le decimos a Room que esta clase es una DAO (Data Access Objet)
interface GeoDao {

    @Query("SELECT * FROM tbl_Geo ORDER BY user_id ASC") // Consulta para obtener todos las Geo de la BD local con Room
    suspend fun getAllGeo():List<GeoEntity>//Se usa suspend por que vamos a trabajar con las corrutinas.
    //por otro lado, usamos GeoEntity por que es una entidad, si usaramos la clase GeoModel no nos funciona

    @Query("SELECT * FROM tbl_Geo WHERE user_id=:user_id ") // Consulta a la base de datos para obtener los datos Geo
    // que tenga el id de usario correspondiente
    suspend fun getGeo(user_id: Int): GeoEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE) // Si se intentara insertar un objeto de tipo GeoEntity con un id (PrimaryKey)
    //ya existente en la base de datos entonces no sale un error sino que el registro se reemplaza por el nuevo, para lo anterior
    // usamos "onConflict = OnConflictStrategy.REPLACE"
    suspend fun insertGeo(geoEntity : GeoEntity) //Se usa suspend por que vamos a trabajar con las corrutinas
    // esta función insertara un solo objeto de tipo GeoEntity
    //por otro lado, usamos GeoEntity por que es una entidad, si usaramos la clase GeoModel no nos funciona

    @Insert(onConflict = OnConflictStrategy.REPLACE ) // Si se intentara insertar un objeto de tipo GeoEntity con un id (PrimaryKey)
    //ya existente en la base de datos entonces no sale un error sino que el registro se reemplaza por el nuevo, para lo anterior
    // usamos "onConflict = OnConflictStrategy.REPLACE"
    suspend fun insertAllGeo(listGeoEntity : List<GeoEntity>) //Se usa suspend por que vamos a trabajar con las corrutinas
    // esta función insertará una lista de objetos de tipo GeoEntity
    //por otro lado, usamos GeoEntity por que es una entidad, si usaramos la clase GeoModel no nos funciona

    @Update
    suspend fun updateGeo(geoEntity : GeoEntity) // Room es los suficientemente inteligente para saber
    // que el id de GeoEntity recibido es la Primary Key de la base de datos, asi que buscara por esa primary key
    // y actualizara el registro.

    @Query("DELETE FROM tbl_Geo WHERE user_id =:user_id") // Elimina los registros existentes en esa tabla de la BD local
    // y donde la condicion se cumpla
    suspend fun deleteGeo(user_id: Int) // Si en lugar de recibir un Int, recibieramos un objeto de tipo GeoEntity,
    // Room es los suficientemente inteligente para saber que el id del objeto GeoEntity es la Primary Key de la base de datos,
    // asi que buscara por esa primary key y eliminara ese registro, claro esta que para que lo anterior fucione,
    // debemos usar la anotción @Delete y no @Query

    @Query("DELETE FROM tbl_Geo") // Elimina todos los registros existente em la esa tabla de la BD local con Room
    suspend fun deleteAllGeo() //Se usa suspend por que vamos a trabajar con las corrutinas.
}