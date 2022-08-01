package co.com.personal.hnino.pruebaceiba.data.database.dao

import androidx.room.*
import co.com.personal.hnino.pruebaceiba.data.database.model.CompanyEntity

@Dao //Con esta anotación le decimos a Room que esta clase es una DAO (Data Access Objet)
interface CompanyDao {

    @Query("SELECT * FROM tbl_Company ORDER BY user_id ASC") // Consulta para obtener todos los objetos Company de la BD local con Room
    suspend fun getAllCompany():List<CompanyEntity>//Se usa suspend por que vamos a trabajar con las corrutinas.
    //por otro lado, usamos CompanyEntity por que es una entidad, si usaramos la clase CompanyModel no nos funciona

    @Query("SELECT * FROM tbl_Company WHERE user_id=:user_id ") // Consulta a la base de datos para obtener los datos Company
    // que tenga el id de usario correspondiente
    suspend fun getCompany(user_id: Int): CompanyEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE) // Si se intentara insertar un objeto de tipo CompanyEntity con un id (PrimaryKey)
    //ya existente en la base de datos entonces no sale un error sino que el registro se reemplaza por el nuevo, para lo anterior
    // usamos "onConflict = OnConflictStrategy.REPLACE"
    suspend fun insertCompany(companyEntity : CompanyEntity) //Se usa suspend por que vamos a trabajar con las corrutinas
    // esta función insertara un solo objeto de tipo CompanyEntity
    //por otro lado, usamos CompanyEntity por que es una entidad, si usaramos la clase CompanyModel no nos funciona

    @Insert(onConflict = OnConflictStrategy.REPLACE ) // Si se intentara insertar un objeto de tipo CompanyEntity con un id (PrimaryKey)
    //ya existente en la base de datos entonces no sale un error sino que el registro se reemplaza por el nuevo, para lo anterior
    // usamos "onConflict = OnConflictStrategy.REPLACE"
    suspend fun insertAllCompany(listCompanyEntity : List<CompanyEntity>) //Se usa suspend por que vamos a trabajar con las corrutinas
    // esta función insertará una lista de objetos de tipo CompanyEntity
    //por otro lado, usamos CompanyEntity por que es una entidad, si usaramos la clase CompanyModel no nos funciona

    @Update
    suspend fun updateCompany(geoEntity : CompanyEntity) // Room es los suficientemente inteligente para saber
    // que el id de CompanyEntity recibido es la Primary Key de la base de datos, asi que buscara por esa primary key
    // y actualizara el registro.

    @Query("DELETE FROM tbl_Company WHERE user_id =:user_id") // Elimina los registros existentes en esa tabla de la BD local
    // y donde la condicion se cumpla
    suspend fun deleteCompany(user_id: Int) // Si en lugar de recibir un Int, recibieramos un objeto de tipo CompanyEntity,
    // Room es los suficientemente inteligente para saber que el id del objeto CompanyEntity es la Primary Key de la base de datos,
    // asi que buscara por esa primary key y eliminara ese registro, claro esta que para que lo anterior fucione,
    // debemos usar la anotación @Delete y no @Query

    @Query("DELETE FROM tbl_Company") // Elimina todos los registros existente em la esa tabla de la BD local con Room
    suspend fun deleteAllCompany() //Se usa suspend por que vamos a trabajar con las corrutinas.
}