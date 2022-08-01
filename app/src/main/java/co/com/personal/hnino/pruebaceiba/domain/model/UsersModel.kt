package co.com.personal.hnino.pruebaceiba.domain.model

import co.com.personal.hnino.pruebaceiba.data.database.model.AddressEntity
import co.com.personal.hnino.pruebaceiba.data.database.model.CompanyEntity
import co.com.personal.hnino.pruebaceiba.data.database.model.GeoEntity
import co.com.personal.hnino.pruebaceiba.data.model.entidades.AddressFromApi
import co.com.personal.hnino.pruebaceiba.data.model.entidades.CompanyFromApi
import co.com.personal.hnino.pruebaceiba.data.model.entidades.GeoFromApi
import co.com.personal.hnino.pruebaceiba.data.model.entidades.UsersFromApi

//Este es el modelo de datos final con el que la UI y dominio van a trabajar, de tal forma que si hay que
// cambiar el modelo de datos la base de datos, o de Retrifit no va a pasar nada ya que son modelos de datos
// independientes y la información que llegue al dominio va a ser siempre este modelo de datos

//---------------------------------------------------------------------------------------//
data class UsersModel(
    val id: Int,
    val nombre: String,
    val username: String,
    val email: String,
    val address: AddressModel,
    val phone: String,
    val website: String,
    val company: CompanyModel
)

fun UsersFromApi.converterToUserModel(): UsersModel{
    var usersModel: UsersModel = UsersModel(id=id, nombre=nombre, username=username, email=email,
    address = address.converterToAddressModel(), phone=phone, website=website,
    company=company.converterToCompanyModel())
    return usersModel
}
//---------------------------------------------------------------------------------------//
data class AddressModel(
    val street: String,
    val suite: String,
    val city: String,
    val zipcode: String,
    val geo: GeoModel
)

fun AddressFromApi.converterToAddressModel(): AddressModel{//Función de extensión util para la funcionalidad del maper
    var addressModel: AddressModel = AddressModel(street=street, suite=suite, city=city, zipcode=zipcode,
    geo=geo.converterToGeoModel()) // los parametros azules
    // son los asociados a esta misma clase, y los morados, son los asociados a al objeto de la clase AddressFromApi,
    // lo anterior debido a que estamos trabajando con una funsión de extensión de la clase AddressFromApi
    return addressModel
}

fun AddressEntity.converterToAddressModel(geoEntity: GeoEntity): AddressModel{ //Función de extensión util para la funcionalidad del maper
    val addressModel: AddressModel = AddressModel(street=street, suite=suite, city=city, zipcode=zipcode,
    geo= geoEntity.converterToGeoModel()) // los parametros azules
    // son los asociados a esta misma clase, y los morados, son los asociados al objeto de la clase AddressEntity,
    // lo anterior debido a que estamos trabajando con una funsión de extensión de la clase AddressEntity
    return addressModel
}
//---------------------------------------------------------------------------------------//
data class GeoModel(
    val lat: String,
    val lng: String
)

fun GeoFromApi.converterToGeoModel(): GeoModel {//Función de extensión util para la funcionalidad del maper
    var geoModel: GeoModel = GeoModel(lat=lat, lng=lng) // los parametros azules
    // son los asociados a esta misma clase, y los morados, son los asociados al objeto de la clase GeoFromApi,
    // lo anterior debido a que estamos trabajando con una funsión de extensión de la clase GeoFromApi
    return geoModel
}

fun GeoEntity.converterToGeoModel(): GeoModel { //Función de extensión util para la funcionalidad del maper
    var geoModel: GeoModel = GeoModel(lat=lat, lng=lng) // los parametros azules
    // son los asociados a esta misma clase, y los morados, son los asociados al objeto de la clase GeoEntity,
    // lo anterior debido a que estamos trabajando con una funsión de extensión de la clase GeoEntity
    return  geoModel
}
//---------------------------------------------------------------------------------------//
data class CompanyModel(
    val name: String,
    val catchPhrase: String,
    val bs: String
)

fun CompanyFromApi.converterToCompanyModel() : CompanyModel { //Función de extensión util para la funcionalidad del maper
    val companyModel: CompanyModel = CompanyModel(name=name, catchPhrase = catchPhrase, bs = bs) // los parametros azules
    // son los asociados a esta misma clase, y los morados, son los asociados al objeto de la clase CompanyFromApi,
    // lo anterior debido a que estamos trabajando con una funsión de extensión de la clase CompanyFromApi
    return  companyModel
}

fun CompanyEntity.converterToCompanyModel() : CompanyModel { //Función de extensión util para la funcionalidad del maper
    val companyModel: CompanyModel = CompanyModel(name=name, catchPhrase = catchPhrase, bs = bs) // los parametros azules
    // son los asociados a esta misma clase, y los morados, son los asociados al objeto de la clase CompanyEntity,
    // lo anterior debido a que estamos trabajando con una funsión de extensión de la clase CompanyEntity
    return  companyModel
}

