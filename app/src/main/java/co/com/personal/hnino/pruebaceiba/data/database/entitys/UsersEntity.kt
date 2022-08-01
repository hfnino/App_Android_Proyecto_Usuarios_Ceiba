package co.com.personal.hnino.pruebaceiba.data.database.model

import androidx.room.*
import co.com.personal.hnino.pruebaceiba.domain.model.AddressModel
import co.com.personal.hnino.pruebaceiba.domain.model.CompanyModel
import co.com.personal.hnino.pruebaceiba.domain.model.GeoModel
import co.com.personal.hnino.pruebaceiba.domain.model.UsersModel

// Este es el modelo de datos que usaremos para trabajar con la base de datos local (SQLite) por medio de la libreria Room

/*
@PrimaryKey(autoGenerate = true) =>  cuando usamos la etiqueta @PrimaryKey antes de un atribito , por ejemplo antes del atributo
val id: Int = 0, estamos configurando ese campo para que sea unico y no se podra repetir. por otro lado, cuando usamos la
propiedad "autoGenerate = true", estamos configurando la Entidad para que cuando se inserte un objeto en la base de datos
de dicha clase/Entidad, no necesitamos pasarle el valor del campo "id" ya que este valor se pondra automaticamente
y nunca se repetira de tal forma que podemos asegurarnos de que cada objeto es unico.

@ColumnInfo(name= "xxxxxxxxx") => cuando usamos esta etiqueta, estamos configurando como debe llamarse el campo/columna en la
base de datos, si no lo colocamos, room usara el nombre del la variable correspondiente pero lo recomendable es siempre usar
dicha etiqueta debido a que cuando generemos la versión en release podemos tener problemas.

@NonNull => esta etiqueta configura el campo correspondiente para decirle que no puede ser null
*/

//*********************************************************************************************//
@Entity(tableName = "tbl_Users")// Declaramos que la clase UsersEntity es una entidad asociada a la tabla
//// tbl_Users de la base de datos, y esta va a ser reconocida por Room
data class UsersEntity (

    @PrimaryKey
    @ColumnInfo(name= "id")
    val id: Int,

    @ColumnInfo(name= "nombre")
    val nombre: String = "Sin Información",

    @ColumnInfo(name= "username")
    val username: String,

    @ColumnInfo(name= "email")
    val email: String,

    @ColumnInfo(name= "phone")
    val phone: String,

    @ColumnInfo(name= "website")
    val website: String,

)

fun UsersModel.converterTiUsersEntity(): UsersEntity{ //Función de extensión util para la funcionalidad del maper
    val usersEntity: UsersEntity = UsersEntity(id=id, nombre=nombre, username=username, email=email,
    phone=phone, website=website)
    return usersEntity
}

//*********************************************************************************************//
@Entity(tableName = "tbl_Address",
    foreignKeys = [ForeignKey(entity = UsersEntity::class, parentColumns = ["id"], childColumns = ["user_id"])],
    indices = [Index("user_id")])
// Declaramos que la clase AddressEntity es una entidad asociada a la tabla tbl_Address de la base de datos,
// y esta va a ser reconocida por Room, ademas cuenta con una llave foranea en la entidad UsersEntity
// asociada al campo "id" y se relaciona con el campo "user_id" de la entidad AddressEntity

data class AddressEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name= "id")
    val id: Int = 0,

    @ColumnInfo(name= "user_id")
    val user_id: Int,

    @ColumnInfo(name= "street")
    val street: String,

    @ColumnInfo(name= "suite")
    val suite: String,

    @ColumnInfo(name= "city")
    val city: String,

    @ColumnInfo(name= "zipcode")
    val zipcode: String,
    )

fun AddressModel.converterToAddressEntity(user_id: Int): AddressEntity { //Función de extensión util para la funcionalidad del maper
    val addressEntity: AddressEntity = AddressEntity(user_id = user_id, street = street, suite = suite, city = city,
    zipcode = zipcode)// los parametros azules son los asociados a esta misma clase, y los morados, son los asociados al objeto
    // de la clase AddressModel, lo anterior debido a que estamos trabajando con una funsión de extensión de la clase AddressModel
    return addressEntity
}



//*********************************************************************************************//

@Entity(tableName = "tbl_Geo",
    foreignKeys = [ForeignKey(entity = UsersEntity::class, parentColumns = ["id"], childColumns = ["user_id"])],
    indices = [Index("user_id")])
// Declaramos que la clase GeoEntity es una entidad asociada a la tabla tbl_Geo de la base de datos,
// y esta va a ser reconocida por Room, ademas cuenta con una llave foranea en la entidad UsersEntity
// asociada al campo "id" y se relaciona con el campo "user_id" de la entidad GeoEntity
data class GeoEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name= "id")
    val id: Int = 0,

    @ColumnInfo(name= "user_id")
    val user_id: Int,

    @ColumnInfo(name= "lat")
    val lat: String,

    @ColumnInfo(name= "lng")
    val lng: String
)

fun GeoModel.converterToGeoEntity(user_id: Int): GeoEntity { //Función de extensión util para la funcionalidad del maper
    val geoEntity: GeoEntity = GeoEntity(user_id = user_id, lat = lat, lng = lng) // los parametros azules son los
    // asociados a esta misma clase, y los morados, son los asociados al objeto de la clase GeoModel, lo anterior debido
    // a que estamos trabajando con una funsión de extensión de la clase GeoModel
    return geoEntity
}

//---------------------------------------------------------------------------------------//

@Entity(tableName = "tbl_Company",
    foreignKeys = [ForeignKey(entity = UsersEntity::class, parentColumns = ["id"], childColumns = ["user_id"])],
    indices = [Index("user_id")])
// Declaramos que la clase CompanyEntity es una entidad asociada a la tabla tbl_Company de la base de datos,
// y esta va a ser reconocida por Room, ademas cuenta con una llave foranea en la entidad UsersEntity
// asociada al campo "id" y se relaciona con el campo "user_id" de la entidad CompanyEntity
data class CompanyEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name= "id")
    val id: Int = 0,

    @ColumnInfo(name= "user_id")
    val user_id: Int,

    @ColumnInfo(name= "name")
    val name: String,

    @ColumnInfo(name= "catchPhrase")
    val catchPhrase: String,

    @ColumnInfo(name= "bs")
    val bs: String
)

fun CompanyModel.converterToCompanyEntity(user_id: Int): CompanyEntity { //Función de extensión util para la funcionalidad del maper
    val companyEntity: CompanyEntity = CompanyEntity(user_id = user_id, name = name, catchPhrase = catchPhrase,
    bs = bs) // los parametros azules son los asociados a esta misma clase, y los morados, son los asociados al objeto
    // de la clase GeoModel, lo anterior debido a que estamos trabajando con una funsión de extensión de la clase GeoModel
    return companyEntity
}