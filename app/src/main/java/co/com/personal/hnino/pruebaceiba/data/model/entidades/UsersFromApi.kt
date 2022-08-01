package co.com.personal.hnino.pruebaceiba.data.model.entidades

import com.google.gson.annotations.SerializedName

//Este es el modelo de datos que usaremos para trabajar con Retrofit para capturar toda la información
// asociada a los usuarios por medio de la API

//---------------------------------------------------------------------------------------//
data class UsersFromApi(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val nombre: String,
    @SerializedName("username") val username: String,
    @SerializedName("email") val email: String,
    @SerializedName("address") val address: AddressFromApi,
    @SerializedName("phone") val phone: String,
    @SerializedName("website") val website: String,
    @SerializedName("company") val company: CompanyFromApi

    // desde la API se recibe el por ejemplo el name pero el dato lo almacenamos en la variable 'nombre',
    // simplemente cambiamos el nombre para que la aplicación sea mas facil de entender si asi fuera necesario,
    // en este caso no era necesario, pero se implemento para tener un ejemplo
)
//---------------------------------------------------------------------------------------//
data class AddressFromApi(
    @SerializedName("street") val street: String,
    @SerializedName("suite") val suite: String,
    @SerializedName("city") val city: String,
    @SerializedName("zipcode") val zipcode: String,
    @SerializedName("geo") val geo: GeoFromApi
)
//---------------------------------------------------------------------------------------//
data class GeoFromApi(
    @SerializedName("lat") val lat: String,
    @SerializedName("lng") val lng: String
)
//---------------------------------------------------------------------------------------//
data class CompanyFromApi(
    @SerializedName("name") val name: String,
    @SerializedName("catchPhrase") val catchPhrase: String,
    @SerializedName("bs") val bs: String
)

